package by.natashkinsasha.model.comparator;


import by.natashkinsasha.model.DaySchedule;

import java.util.Comparator;

public class ComparatorDayScheduleByData implements Comparator<DaySchedule>{
    @Override
    public int compare(DaySchedule o1, DaySchedule o2) {
        if (o1.getDate().isBefore(o2.getDate())){
            return -1;
        } else if(o1.getDate().isAfter(o2.getDate())){
            return 1;
        }
        return 0;
    }
}
