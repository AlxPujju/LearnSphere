package com.divyanshu.backend.service;


import com.divyanshu.backend.Exception.NotFoundException;
import com.divyanshu.backend.model.Course;
import com.divyanshu.backend.model.Module;
import com.divyanshu.backend.repository.CourseRepository;
import com.divyanshu.backend.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;


    @Autowired
    private CourseRepository courseRepository;


    public Module addModule(Module module) {
        return moduleRepository.save(module);
    }


    public Module editModule(int moduleId, Module updatedModule) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(()-> new NotFoundException("Module not found"));
        module.setTitle(updatedModule.getTitle());
        module.setDescription(updatedModule.getDescription());
        return moduleRepository.save(module);
    }


    public boolean deleteModule(int moduleId) {
        if (moduleRepository.existsById(moduleId)) {
            moduleRepository.deleteById(moduleId);
            return true;
        }
        return false;
    }


    public List<Module> getModules() {
        return moduleRepository.findAll();
    }


    public Module getModule(int moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(()-> new NotFoundException("Module not Found"));
    }


    public Module assignModuleToCourse(int moduleId, int courseId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(()-> new NotFoundException("Module not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new NotFoundException("Course not found"));
        course.addModule(module);
        return moduleRepository.save(module);
    }
    public Page<Module> getModules(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return moduleRepository.findAll(pageable);
    }
}
