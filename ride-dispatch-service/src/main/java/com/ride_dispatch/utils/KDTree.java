package com.ride_dispatch.utils;

import com.ride_dispatch.dto.DriverLocation;

import java.util.*;


public class KDTree {
    private KDTreeNode root;

    public KDTree(List<DriverLocation> driverLocations)
    {
        this.root = buildTree(driverLocations, true);
    }
    private KDTreeNode buildTree(List<DriverLocation> driverLocations, boolean isLatSplit) {
        if (driverLocations.isEmpty()) {
            return null;
        }

        driverLocations.sort(Comparator.comparingDouble(
                d -> isLatSplit ? d.lat() : d.lon(
        )));


        int medianIndex = driverLocations.size() / 2;
        DriverLocation medianLocation = driverLocations.get(medianIndex);

        KDTreeNode node = new KDTreeNode(medianLocation, isLatSplit);
        node.setLeft(buildTree(driverLocations.subList(0, medianIndex), !isLatSplit));
        node.setRight(buildTree(driverLocations.subList(medianIndex + 1, driverLocations.size()), !isLatSplit));

        return node;
    }
    public List<DriverLocation> findNearest(DriverLocation target, int K)
    {
        PriorityQueue<Map.Entry<Double, DriverLocation>> heap = new PriorityQueue<>(
            (a, b) -> Double.compare(b.getKey(), a.getKey())
        );
        search(root, target, K, heap);
        List<DriverLocation> result = new ArrayList<>();
        for (Map.Entry<Double, DriverLocation> entry : heap) {
            result.add(entry.getValue());
        }
        return result;
    }
    private void search(KDTreeNode node, DriverLocation target, int k,
                        PriorityQueue<Map.Entry<Double, DriverLocation>> heap) {
        if (node == null) return;

        double dist = haversine(node.getDriver(), target);
        if (heap.size() < k) {
            heap.offer(new AbstractMap.SimpleEntry<>(dist, node.getDriver()));
        } else if (dist < heap.peek().getKey()) {
            heap.poll();
            heap.offer(new AbstractMap.SimpleEntry<>(dist, node.getDriver()));
        }

        boolean goLeft = node.isLatSplit()
                ? target.lat() < node.getDriver().lat()
                : target.lat() < node.getDriver().lon();

        search(goLeft ? node.getLeft() : node.getRight(), target, k, heap);

        // Check if we need to search the other subtree
        double axisDist = node.isLatSplit()
                ? Math.abs(target.lat() - node.getDriver().lat())
                : Math.abs(target.lon() - node.getDriver().lon());

        // convert axisDist from degrees to km roughly (~111 km per degree)
        if (heap.size() < k || axisDist * 111 < heap.peek().getKey()) {
            search(goLeft ? node.getRight() : node.getLeft(), target, k, heap);
        }
    }

    private double haversine(DriverLocation driverLocations, DriverLocation target) {
        double R = 6371; // Earth radius in km
        double dLat = Math.toRadians(target.lat() - driverLocations.lat());
        double dLon = Math.toRadians(target.lon() - driverLocations.lon());
        double lat1 = Math.toRadians(driverLocations.lat());
        double lat2 = Math.toRadians(target.lat());

        double aComp = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dLon / 2), 2);
        return R * 2 * Math.atan2(Math.sqrt(aComp), Math.sqrt(1 - aComp));
    }
}
