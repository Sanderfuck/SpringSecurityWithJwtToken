package com.sander.controller;

import com.sander.model.Developer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {

    private List<Developer> developers = Stream.of(
            new Developer(1L, "Zheka", "Kyrwa"),
            new Developer(2L, "Dashka", "Pizduk"),
            new Developer(3L, "Vados", "Kontik")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Developer> getAll() {
        return developers;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('developers:read')")
    private Developer getById(@PathVariable Long id) {
        return developers.stream().filter(developer -> developer.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public Developer create(@RequestBody Developer developer) {
        this.developers.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public void deleteById(@PathVariable Long id) {
        this.developers.removeIf(developer -> developer.getId().equals(id));
    }
}
