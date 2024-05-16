# Data-Warehousing-and-Business-Intelligence-for-Spotify-Data


## Spotify Data Warehouse
This project aims to enhance user engagement and content discovery on Spotify by leveraging its extensive dataset. By analyzing user preferences, identifying music industry trends, and providing personalized recommendations based on user behavior and other attributes, we seek to improve the overall user experience on the platform.

![spotify-image](https://github.com/shreyasd30/Data-Warehousing-and-Business-Intelligence-for-Spotify-Data/assets/56275546/6e6f3544-71ba-48c7-bdcf-1d7f87e9bea8)


## Tools Used
![Talend_logo svg](https://github.com/shreyasd30/Data-Warehousing-and-Business-Intelligence-for-Spotify-Data/assets/56275546/4e46817c-c284-47bb-b268-71d0770d6480) &emsp;         ![spotify_image](https://github.com/shreyasd30/Data-Warehousing-and-Business-Intelligence-for-Spotify-Data/assets/56275546/ba74d85f-9f84-402a-b3a4-1aaec1f45769)   &emsp; ![download](https://github.com/shreyasd30/Data-Warehousing-and-Business-Intelligence-for-Spotify-Data/assets/56275546/77c6778b-fb2e-430b-a6f9-b1cce51767ab) 


### Conceptual Model
The data warehouse is designed with the following conceptual model to store and analyze various aspects of Spotify's data:

**Tracks**
This table stores detailed information about individual tracks, including their duration, popularity, explicitness, and their position within albums.

Id: Unique identifier for the track.
Name: Name of the track.
Duration: Length of the track in milliseconds.
disc_number: The disc number (useful for multi-disc albums).

**Albums**
This table holds attributes related to albums, such as their name, type, release date, and popularity. This enables analysis based on album characteristics.

Album_id: Unique identifier for the album.
Name: Name of the album.
Release_date: Date the album was released.
Album_type: Type of the album (e.g., single, album, compilation).
Popularity: Popularity score of the album.

**AudioFeatures**
This table stores audio features of tracks, allowing for analysis based on the musical properties of tracks.

audio_feature_id: Unique identifier for the track audio feature.
Acousticness: A measure from 0.0 to 1.0 of whether the track is acoustic.
Danceability: A measure from 0.0 to 1.0 of how suitable the track is for dancing.
Energy: A measure from 0.0 to 1.0 of the intensity and activity of the track.
Instrumentalness: A measure from 0.0 to 1.0 of the likelihood of the track being instrumental.
Key: The key the track is in.
Liveness: A measure from 0.0 to 1.0 of the presence of an audience in the recording.
Loudness: The overall loudness of the track in decibels.

**Artist**
This table contains information about artists, including their name, popularity, and followers, facilitating analysis based on artist-related factors.

Artist_id: Unique identifier for the artist.
Name: Name of the artist.
Popularity: Popularity score of the artist.
Followers: Number of followers the artist has.

**Genre**
This table stores different genres of music, enabling analysis based on genre-specific trends and characteristics.

Genre_id: Unique identifier for the genre.
Genre_name: Name of the genre.


## Goal
The ultimate goal is to utilize this data warehouse to conduct comprehensive analyses that reveal user preferences and trends within the music industry. These insights can then be used to provide personalized recommendations and enhance overall user engagement on Spotify.


The link for the dataset can be found here - [Spotify Music Dataset](https://www.kaggle.com/datasets/maltegrosse/8-m-spotify-tracks-genre-audio-features/data)



## Spotify Music data - Dashboard
The link to the dashboard can be found here - [Spotify Music Data Dashboard](https://public.tableau.com/app/profile/shreyas.dsshmukh/viz/Spotify_DB_1/Dashboard1)



**This project is structured to be easily extended and modified to accommodate additional analyses and data sources as needed. Contributions and suggestions for improvements are welcome.**
