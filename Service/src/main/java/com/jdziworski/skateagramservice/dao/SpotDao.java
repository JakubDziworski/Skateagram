package com.jdziworski.skateagramservice.dao;

import com.jdziworski.skateagramservice.domain.Spot;

/**
 * Created by kuba on 23.10.2015.
 */
public interface SpotDao {
    Spot findById(int id);
}
