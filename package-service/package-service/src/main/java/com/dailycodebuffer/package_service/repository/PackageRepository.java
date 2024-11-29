package com.dailycodebuffer.package_service.repository;

import com.dailycodebuffer.package_service.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findByPackageType(String packageType);
}
