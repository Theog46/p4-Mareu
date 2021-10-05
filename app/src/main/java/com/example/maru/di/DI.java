package com.example.maru.di;

import com.example.maru.service.DummyMeetingApiService;
import com.example.maru.service.MeetingApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static final MeetingApiService service = new DummyMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     *
     * @return service
     */
    public static MeetingApiService getApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
     *
     * @return DummyMaReuApiService
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}