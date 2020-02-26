package org.mflix.forecast.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mflix.forecast.entity.TestEntity;
import org.mflix.forecast.properties.ApplicationProperties;
import org.mflix.forecast.repository.TestRepository;
import org.mflix.forecast.view.TestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public TestService(TestRepository testRepository, ApplicationProperties applicationProperties) {
        this.testRepository = testRepository;
        this.applicationProperties = applicationProperties;
    }

    public TestView createByView(TestView testView) {
        var testEntity = new TestEntity(testView.getText());
        testEntity = testRepository.save(testEntity);
        testView.setId(testEntity.getId());
        return testView;
    }

    public List<TestView> readAll() {
        return testRepository.findAll().stream().map((testEntity) -> {
            return new TestView(testEntity.getId(), testEntity.getText());
        }).collect(Collectors.toList());
    }

    public Page<TestView> readAllWithPage(Pageable pageable) {
        var testViewList = testRepository.findAll(pageable).map((testEntity) -> {
            return new TestView(testEntity.getId(), testEntity.getText());
        }).toList();
        return new PageImpl<>(testViewList, pageable, testViewList.size());
    }

    public TestView readById(long id) {
        var testEntity = testRepository.findById(id).orElseThrow();
        return new TestView(testEntity.getId(), testEntity.getText());
    }

    public TestView updateByIdAndView(long id, TestView testView) {
        var testEntity = testRepository.findById(id).orElseThrow();
        testEntity.setText(testView.getText());
        testEntity = testRepository.save(testEntity);
        testView.setId(testEntity.getId());
        return testView;
    }

    public void deleteById(long id) {
        testRepository.deleteById(id);
    }

    public FileSystemResource getFile(String filename) {
        filename = applicationProperties.getTestPath() + filename;
        return new FileSystemResource(filename);
    }
}