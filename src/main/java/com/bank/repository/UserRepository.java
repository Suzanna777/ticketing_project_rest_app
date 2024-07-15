package com.bank.repository;

import com.bank.entityTable.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<UserTable,Long>{


    List<UserTable> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);

    UserTable findByUserNameAndIsDeleted(String username, Boolean deleted);


    @Transactional
    void deleteByUserName(String username);

    List<UserTable> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);

}
