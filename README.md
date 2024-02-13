# Event Schceduler Project

## About

EventScheduler is an event organizing application that handles multiple event requests and sorts them into a daily calendar.

## Restrictions/Rules

Multiple events can take place in a day.

Day starts at 09:00 and ends at 17:00.

Morning events occur between 09:00 - 12:00.

Noon events occur between 13:00 - 17:00.

There is lunch break every day between 12:00-13:00.

Event periods are described in minutes or referred as lightning(5 min fast presentations).

There are no breaks between events.

If there is any time left at the end of the day after 16:00, a networking event might occur.


## Technical Info

A single REST API accepts event input in JSON format.

It calculates and organizes data using best fit bin packing algorithm.

API returns a Calendar object in JSON format.

## Object Hierarchy

- Calendar -> The uppermost object, contains dates(days).

- Dates -> Represents a single day 9:00AM - 5:00PM, contains tracks.

- Tracks -> Represents the part of a day (Before Noon - After Noon), contains events.

- Events -> Actual event objects with name,duration and start times.


## Usage

Execute downloaded jar file:

```bash
java -jar event-scheduler-0.0.1-SNAPSHOT.jar
```

## Postman

Download postman configuration : 



## Example Data

Endpoint : 
```sh
http://localhost:8080/schedule/create
```

Request : 
```json
[
  {
    "name":"Architecting Your Codebase",
    "duration": "60min"
  },
  {
    "name":"Overdoing it in Python",
    "duration": "45min"
  }
]
```

Response : 
```json
"calendar": {
        "dateList": [
            {
                "name": "DATE-1",
                "trackList": [
                    {
                        "eventList": [
                            {
                                "name": "Architecting Your Codebase",
                                "startTime": "9:00 AM",
                                "duration": 60
                            },
                            {
                                "name": "Overdoing it in Python",
                                "startTime": "10:00 AM",
                                "duration": 45
                            },
                            {
                                "name": "Flavors of Concurrency in Java",
                                "startTime": "10:45 AM",
                                "duration": 30
                            },
                            {
                                "name": "Ruby Errors from Mismatched Gem Versions",
                                "startTime": "11:15 AM",
                                "duration": 45
                            },
                            {
                                "name": "Lunch",
                                "startTime": "12:00PM",
                                "duration": 60
                            }
                        ]
                    },
                    {
                        "eventList": [
                            {
                                "name": "JUnit 5 - Shaping the Future of Testing on the JVM",
                                "startTime": "1:00 PM",
                                "duration": 45
                            },
                            {
                                "name": "Communicating Over Distance",
                                "startTime": "1:45 PM",
                                "duration": 60
                            },
                            {
                                "name": "AWS Technical Essentials",
                                "startTime": "2:45 PM",
                                "duration": 45
                            },
                            {
                                "name": "Continuous Delivery",
                                "startTime": "3:30 PM",
                                "duration": 30
                            },
                            {
                                "name": "Monitoring Reactive Applications",
                                "startTime": "4:00 PM",
                                "duration": 30
                            },
                            {
                                "name": "Cloud Native Java",
                                "startTime": "4:30 PM",
                                "duration": 5
                            },
                            {
                                "name": "Networking Event",
                                "startTime": "4:35 PM",
                                "duration": 25
                            }
                        ]
                    }
                ]
            }
        ]
    }
}
```



