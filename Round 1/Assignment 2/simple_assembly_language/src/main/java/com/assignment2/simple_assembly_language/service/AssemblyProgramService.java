package com.assignment2.simple_assembly_language.service;

import com.assignment2.simple_assembly_language.model.AssemblyProgram;
import com.assignment2.simple_assembly_language.repository.AssemblyProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AssemblyProgramService {

    @Autowired
    private AssemblyProgramRepository repository;

    private final Map<String, Integer> registers = new HashMap<>();

    public String executeStatement(String statement) {
        return processInstruction(statement, registers);
    }

    public AssemblyProgram execute(String programText) {
        String[] instructions = programText.split("\n");
        Map<String, Integer> localRegisters = new HashMap<>();
        StringBuilder resultBuilder = new StringBuilder();

        boolean ifConditionExecute = true;

        for (String instruction : instructions) {
            if (instruction.isEmpty()) {
                continue;
            }

            String[] parts = instruction.split("[ ,]+");
            String command = parts[0];

            if (command.equals("ifgt")) {
                String register = parts[1];
                int comparisonValue = Integer.parseInt(parts[2]);

                int registerValue = localRegisters.getOrDefault(register, 0);
                ifConditionExecute = registerValue > comparisonValue;

                continue;
            } else if (command.equals("Endif")) {
                ifConditionExecute = true;
                continue;
            }

            if (ifConditionExecute) {
                resultBuilder.append(processInstruction(instruction, localRegisters)).append("\n");
            }
        }

        AssemblyProgram assemblyProgram = new AssemblyProgram();
        assemblyProgram.setProgramText(programText);
        assemblyProgram.setResult(showAllRegisters(localRegisters));

        return repository.save(assemblyProgram);
    }

    private String processInstruction(String instruction, Map<String, Integer> registers) {
        String[] parts = instruction.split("[ ,]+");
        String command = parts[0];

        switch (command) {
            case "MV":
                String reg = parts[1];
                int value = Integer.parseInt(parts[2].replace("#", ""));
                registers.put(reg, value);
                return "Moved " + value + " to " + reg;

            case "ADD":
                String reg1 = parts[1];
                if (parts[2].startsWith("#")) {
                    int constValue = Integer.parseInt(parts[2].replace("#", ""));
                    registers.put(reg1, registers.getOrDefault(reg1, 0) + constValue);
                    return "Added " + constValue + " to " + reg1;
                } else {
                    String reg2 = parts[2];
                    registers.put(reg1, registers.getOrDefault(reg1, 0) + registers.getOrDefault(reg2, 0));
                    return "Added value of " + reg2 + " to " + reg1;
                }

            case "SHOW":
                return showAllRegisters(registers);

            case "CLEAR":
                registers.clear();
                return "Cleared all registers";

            default:
                return "Unknown command: " + command;
        }
    }

    private String showAllRegisters(Map<String, Integer> registers) {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}
