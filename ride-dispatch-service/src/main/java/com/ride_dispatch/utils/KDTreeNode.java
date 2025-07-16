package com.ride_dispatch.utils;

import com.ride_dispatch.dto.DriverLocation;
import lombok.Getter;
import lombok.Setter;

public class KDTreeNode {
    @Setter
    @Getter
    DriverLocation driver;
    @Setter
    @Getter
    KDTreeNode left, right;
    @Setter
    @Getter
    boolean isLatSplit;

    public KDTreeNode(DriverLocation driver, boolean isLatSplit){
        this.driver = driver;
        this.isLatSplit = isLatSplit;
    }

}
