package com.bank.entityTable;

import com.bank.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted=false")
public class TaskTable extends BaseEntity{
    private String taskSubject;
    private String taskDetail;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_employee_id")
    private UserTable assignedEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectTable project;

}
