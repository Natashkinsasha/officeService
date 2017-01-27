package by.natashkinsasha.model.comparator;


import by.natashkinsasha.model.Reservations;

import java.util.Comparator;

public class ComparatorReservationByTime implements Comparator<Reservations>{
    @Override
    public int compare(Reservations o1, Reservations o2) {
        if (o1.getStartDuration().isBefore(o2.getStartDuration())){
            return -1;
        } else if(o1.getStartDuration().isAfter(o2.getStartDuration())){
            return 1;
        }
        return 0;
    }
}
