package com.cxy.service;

import com.cxy.entry.mongoEntry.MongoCinema;

import java.util.List;

public interface MongoCinemaService {
    boolean add(MongoCinema mongoCinema);

    List<MongoCinema> info(double x, double y, int page, int limit, String sort);

    List<MongoCinema> infoByFilmID(double x, double y, int page, int limit, String sort, String filmName);

    boolean del(Long id);


}
