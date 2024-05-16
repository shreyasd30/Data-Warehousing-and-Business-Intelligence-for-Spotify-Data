# Data-Warehousing-and-Business-Intelligence-for-Spotify-Data


##Spotify Data Warehouse
This project aims to enhance user engagement and content discovery on Spotify by leveraging its extensive dataset. By analyzing user preferences, identifying music industry trends, and providing personalized recommendations based on user behavior and other attributes, we seek to improve the overall user experience on the platform.

###Conceptual Model
The data warehouse is designed with the following conceptual model to store and analyze various aspects of Spotify's data:

**Tracks**
This table stores detailed information about individual tracks, including their duration, popularity, explicitness, and their position within albums.

Id: Unique identifier for the track
Name: Name of the track
Duration: Length of the track in milliseconds
disc_number: The disc number (useful for multi-disc albums)

**Albums**
This table holds attributes related to albums, such as their name, type, release date, and popularity. This enables analysis based on album characteristics.

Album_id: Unique identifier for the album
Name: Name of the album
Release_date: Date the album was released
Album_type: Type of the album (e.g., single, album, compilation)
Popularity: Popularity score of the album

**AudioFeatures**
This table stores audio features of tracks, allowing for analysis based on the musical properties of tracks.

Track_id: Unique identifier for the track
Acousticness: A measure from 0.0 to 1.0 of whether the track is acoustic
Danceability: A measure from 0.0 to 1.0 of how suitable the track is for dancing
Energy: A measure from 0.0 to 1.0 of the intensity and activity of the track
Instrumentalness: A measure from 0.0 to 1.0 of the likelihood of the track being instrumental
Key: The key the track is in
Liveness: A measure from 0.0 to 1.0 of the presence of an audience in the recording
Loudness: The overall loudness of the track in decibels

**Artist**
This table contains information about artists, including their name, popularity, and followers, facilitating analysis based on artist-related factors.

Artist_id: Unique identifier for the artist
Name: Name of the artist
Popularity: Popularity score of the artist
Followers: Number of followers the artist has.

**Genre**
This table stores different genres of music, enabling analysis based on genre-specific trends and characteristics.

Genre_id: Unique identifier for the genre
Genre_name: Name of the genre


##Goal
The ultimate goal is to utilize this data warehouse to conduct comprehensive analyses that reveal user preferences and trends within the music industry. These insights can then be used to provide personalized recommendations and enhance overall user engagement on Spotify.

**This project is structured to be easily extended and modified to accommodate additional analyses and data sources as needed. Contributions and suggestions for improvements are welcome.**

The link for the dataset can be found here - Spotify Music Dataset https://www.kaggle.com/datasets/maltegrosse/8-m-spotify-tracks-genre-audio-features/data
