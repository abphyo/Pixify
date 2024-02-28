package com.biho.pixify.core.host.danbooru

import com.biho.pixify.core.host.danbooru.dtos.login.ProfileDto
import com.biho.pixify.core.host.danbooru.dtos.login.ProfileSettingsField
import com.biho.pixify.core.host.danbooru.dtos.post.PostDto
import com.biho.pixify.core.host.danbooru.dtos.tag.TagAutoCompleteDtoList
import com.biho.pixify.core.host.danbooru.dtos.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/posts/{id}.json")
    suspend fun getPost(
        @Path("id") id: Int
    ): Response<PostDto>

    @GET("/posts.json")
    suspend fun getPosts(
        @Query("tags") tags: String,
        @Query("page") page: Int,
        @Query("limit") postsPerPage: Int = 20,
        @Header("force-cache") forceCache: Boolean = false,
        @Header("cache-control") cacheControl: String = "",
    ): Response<List<PostDto>>

    @GET("/autocomplete.json")
    suspend fun getTagsAutoComplete(
        @Query("search[query]") query: String,
        @Query("search[type]") type: String = "tag_query",
        @Query("limit") limit: Int = 20,
        @Query("version") version: Int = 1
    ): Response<TagAutoCompleteDtoList>

//    @GET("/tags.json")
//    suspend fun getTags(
//        @Query("search[name][]") tags: List<String>,
//        @Query("limit") limit: Int = 100,
//    ): Response<List<DanbooruTag>>
//
//    @GET("/favorites.json")
//    suspend fun getFavorites(
//        @Query("search[post_id]") postId: Int,
//        @Query("search[user_id]") userId: Int,
//    ): Response<List<DanbooruFavorite>>

    @GET("/profile.json")
    suspend fun login(
        @Query("login") login: String,
        @Query("api_key") apiKey: String
    ): Response<ProfileDto>

    @GET("/profile.json")
    suspend fun getProfile(
        @Header("Authorization") credentials: String? = null,
    ): Response<ProfileDto>

    @GET("/users/{id}.json")
    suspend fun getUser(@Path("id") id: Int): Response<UserDto>

    @PATCH("/users/{id}.json")
    suspend fun updateProfileSettings(
        @Path("id") id: Int,
        @Body data: ProfileSettingsField,
    ): Response<Unit>
//
//    @GET("/favorite_groups.json")
//    suspend fun getFavoriteGroups(
//        @Query("search[creator_id]") creatorId: Int,
//        @Header("cache-control") cacheControl: CacheControl,
//        @Header("force-cache") forceCache: Boolean = true,
//    ): Response<List<DanbooruFavoriteGroup>>

    @POST("/favorites.json")
    suspend fun addToFavorites(
        @Query("post_id") postId: Int,
    ): Response<Unit>

    @DELETE("/favorites/{post_id}.json")
    suspend fun deleteFromFavorites(
        @Path("post_id") postId: Int,
    ): Response<Unit>

//    @GET("/post_votes.json")
//    suspend fun getPostVotes(
//        @Query("search[post_id]") postId: Int,
//        @Query("search[user_id]") userId: Int,
//    ): Response<List<DanbooruPostVote>>

    @POST("/posts/{post_id}/votes.json")
    suspend fun votePost(
        @Path("post_id") postId: Int,
        @Query("score") score: Int,
    ): Response<Unit>

    @DELETE("posts/{post_id}/votes.json")
    suspend fun unvotePost(
        @Path("post_id") postId: Int,
    ): Response<Unit>

    @PUT("/favorite_groups/{favorite_group_id}/add_post.json")
    suspend fun addPostToFavoriteGroup(
        @Path("favorite_group_id") favoriteGroupId: Int,
        @Query("post_id") postId: Int,
    ): Response<Unit>

    @PUT("/favorite_groups/{favorite_group_id}/remove_post.json")
    suspend fun removePostFromFavoriteGroup(
        @Path("favorite_group_id") favoriteGroupId: Int,
        @Query("post_id") postId: Int,
    ): Response<Unit>

//    @POST("/favorite_groups.json")
//    suspend fun createNewFavoriteGroup(
//        @Query("favorite_group[name]") name: String,
//        @Query("favorite_group[post_ids_string]") postIds: String,
//    ): Response<DanbooruFavoriteGroup>

    @PUT("/favorite_groups/{favorite_group_id}.json")
    suspend fun editFavoriteGroup(
        @Path("favorite_group_id") favoriteGroupId: Int,
        @Query("favorite_group[name]") name: String,
        @Query("favorite_group[post_ids_string]") postIds: String,
    ): Response<Unit>

    @DELETE("/favorite_groups/{favorite_group_id}.json")
    suspend fun deleteFavoriteGroup(
        @Path("favorite_group_id") favoriteGroupId: Int,
    ): Response<Unit>
}