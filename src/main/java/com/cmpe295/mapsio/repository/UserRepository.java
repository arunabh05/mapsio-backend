package com.cmpe295.mapsio.repository;

import com.cmpe295.mapsio.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author arunabh.shrivastava
 */
public interface UserRepository extends MongoRepository<User, String> {
}

