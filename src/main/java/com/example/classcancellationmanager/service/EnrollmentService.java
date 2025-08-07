package com.example.classcancellationmanager.service;

import com.example.classcancellationmanager.entity.Enrollment;
import com.example.classcancellationmanager.mapper.EnrollmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Transactional
    public void updateEnrollments(Long studentId, int academicYear, Long termId, List<Long> newClassIds) {
        // 現在の履修情報を取得
        List<Enrollment> currentEnrollments = enrollmentMapper.findByStudentIdAndYearAndTerm(studentId, academicYear, termId);
        List<Long> currentClassIds = currentEnrollments.stream()
                                                        .map(Enrollment::getClassId)
                                                        .collect(Collectors.toList());

        // 削除する履修情報
        List<Long> classIdsToDelete = currentClassIds.stream()
                                                    .filter(id -> !newClassIds.contains(id))
                                                    .collect(Collectors.toList());

        // 追加する履修情報
        List<Long> classIdsToAdd = newClassIds.stream()
                                                .filter(id -> !currentClassIds.contains(id))
                                                .collect(Collectors.toList());

        // 削除処理
        if (!classIdsToDelete.isEmpty() || !classIdsToAdd.isEmpty()) { // 変更があった場合のみ削除・追加を実行
            enrollmentMapper.deleteByStudentIdAndYearAndTerm(studentId, academicYear, termId);
        }

        // 追加処理
        for (Long classId : newClassIds) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudentId(studentId);
            enrollment.setClassId(classId);
            enrollment.setAcademicYear(academicYear);
            enrollment.setTermId(termId);
            enrollmentMapper.insert(enrollment);
        }
    }
}