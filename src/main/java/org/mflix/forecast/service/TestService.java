package org.mflix.forecast.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mflix.forecast.entity.TestEntity;
import org.mflix.forecast.repository.TestRepository;
import org.mflix.forecast.view.TestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public TestView create(TestView testView) {
        var testEntity = new TestEntity(testView.getText());
        testEntity = testRepository.save(testEntity);
        testView.setId(testEntity.getId());
        return testView;
    }

    public TestView read(long id) {
        var testEntity = testRepository.findById(id).orElseThrow();
        var testView = new TestView(testEntity.getId(), testEntity.getText());
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

    public TestView update(long id, TestView testView) {
        var testEntity = testRepository.findById(id).orElseThrow();
        testEntity.setText(testView.getText());
        testEntity = testRepository.save(testEntity);
        testView.setId(testEntity.getId());
        return testView;
    }

    public void delete(long id) {
        testRepository.deleteById(id);
    }
}