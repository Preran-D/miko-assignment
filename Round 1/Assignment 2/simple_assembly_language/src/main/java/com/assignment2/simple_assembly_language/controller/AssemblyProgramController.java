package com.assignment2.simple_assembly_language.controller;

import com.assignment2.simple_assembly_language.model.AssemblyProgram;
import com.assignment2.simple_assembly_language.service.AssemblyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/assembly")
public class AssemblyProgramController {

    @Autowired
    private AssemblyProgramService service;

    @PostMapping("/execute")
    public ResponseEntity<AssemblyProgram> executeProgram(@RequestBody Map<String, String> request) {
        String programText = request.get("programText");
        AssemblyProgram assemblyProgram = service.execute(programText);
        return ResponseEntity.ok(assemblyProgram);
    }

    @PostMapping("/statement")
    public ResponseEntity<String> executeStatement(@RequestBody Map<String, String> request) {
        String statement = request.get("statement");
        String result = service.executeStatement(statement);
        return ResponseEntity.ok(result);
    }
}
