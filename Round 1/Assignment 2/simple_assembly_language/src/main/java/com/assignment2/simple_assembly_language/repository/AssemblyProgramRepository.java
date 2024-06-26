package com.assignment2.simple_assembly_language.repository;

import com.assignment2.simple_assembly_language.model.AssemblyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssemblyProgramRepository extends JpaRepository<AssemblyProgram, Long> {
}
