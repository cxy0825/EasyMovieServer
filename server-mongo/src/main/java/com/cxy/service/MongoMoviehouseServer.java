package com.cxy.service;

import com.cxy.entry.mongoEntry.MongoMoviehouse;
import com.cxy.result.Result;


public interface MongoMoviehouseServer {
    Result getMovieHouseById(Long movieHouseID);

    Result insertData(MongoMoviehouse mongoMoviehouse);


    Result insertSeatByID(Long movieHouseID, int[] arr);

    Result updateSeatByID(Long movieHouseID, Integer[] arr);
}
