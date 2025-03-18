package org.example.service;

import org.example.model.Event;
import org.example.repository.EventMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class EventService {
    @Resource
    private EventMapper eventMapper;

    @Transactional
    public int createEvent(Event po) {
        return eventMapper.insert(po);
    }

    @Transactional
    public int updateEventById(Event po) {
        return eventMapper.updateById(po);
    }
}
