package com.scsinfinity.udhd.dao.repositories.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.base.UserULBDataEntity;

@Repository
public interface IUserULBDataRepository extends JpaRepository<UserULBDataEntity, Long> {

}
