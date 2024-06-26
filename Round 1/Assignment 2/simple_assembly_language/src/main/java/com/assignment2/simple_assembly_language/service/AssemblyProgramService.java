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

    public AssemblyProgram executeAndSave(String programText) {
        String[] instructions = programText.split("\n");
        Map<String, Integer> registers = new HashMap<>();

        StringBuilder resultBuilder = new StringBuilder();

        for (String instruction : instructions) {
            if (instruction.isEmpty()) {
                continue;
            }
            String[] parts = instruction.split("[ ,]+");
            String command = parts[0];

            switch (command) {
                case "MV":
                    String reg = parts[1];
                    int value = Integer.parseInt(parts[2].replace("#", ""));
                    registers.put(reg, value);
                    break;

                case "ADD":
                    String reg1 = parts[1];
                    if (parts[2].startsWith("#")) {
                        int constValue = Integer.parseInt(parts[2].replace("#", ""));
                        registers.put(reg1, registers.getOrDefault(reg1, 0) + constValue);
                    } else {
                        String reg2 = parts[2];
                        registers.put(reg1, registers.getOrDefault(reg1, 0) + registers.getOrDefault(reg2, 0));
                    }
                    break;

                case "SHOW":
                    resultBuilder.append(showAllRegisters(registers));
                    break;

                default:
                    resultBuilder.append("Unknown command: ").append(command).append("\n");
                    break;
            }
        }

        AssemblyProgram assemblyProgram = new AssemblyProgram();
        assemblyProgram.setProgramText(programText);
        assemblyProgram.setResult(resultBuilder.toString());

        return repository.save(assemblyProgram);
    }

    private String showAllRegisters(Map<String, Integer> registers) {
        StringBuilder resultBuilder = new StringBuilder();
        String sortedRegisters = registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));

        resultBuilder.append(sortedRegisters).append("\n");
        return resultBuilder.toString();
    }
}
