package com.bank.repository;

import com.bank.entityTable.RoleTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository  extends JpaRepository<RoleTable,Long> {


    RoleTable findByDescription(String description);

}
