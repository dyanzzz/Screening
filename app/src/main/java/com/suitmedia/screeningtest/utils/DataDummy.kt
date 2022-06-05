package com.suitmedia.screeningtest.utils

import com.suitmedia.screeningtest.features.screenthree.EventEntity

object DataDummy {
    fun generateDummyEvent(): ArrayList<EventEntity> {
        val data = ArrayList<EventEntity>()
        data.add(EventEntity(
            title = "Card Title 1", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 2", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 3", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 4", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 5", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 6", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 7", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 8", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 9", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 10", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        data.add(EventEntity(
            title = "Card Title 11", body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", date = "15 Jan 2021", time = "9:00 AM", image = "image_event.jpg"
        ))
        return data
    }
}