package com.example.puconnect.di

import com.example.puconnect.data.AuthenticationRepositoryImplementation
import com.example.puconnect.data.EventRepositoryImpl
import com.example.puconnect.data.MessageRepositoryImpl
import com.example.puconnect.data.NetworkRepositoryImpl
import com.example.puconnect.data.PostRepositoryImpl
import com.example.puconnect.data.SkillsRepositoryImpl
import com.example.puconnect.data.UserRepositoryImpl
import com.example.puconnect.domain.repository.AuthenticationRepository
import com.example.puconnect.domain.repository.EventRepository
import com.example.puconnect.domain.repository.MessageRepository
import com.example.puconnect.domain.repository.NetworkRepository
import com.example.puconnect.domain.repository.PostRepository
import com.example.puconnect.domain.repository.SkillsRepository
import com.example.puconnect.domain.repository.UserRepository
import com.example.puconnect.domain.use_cases.AuthenticationUseCases.AuthenticationUserCases
import com.example.puconnect.domain.use_cases.AuthenticationUseCases.FirebaseAuthState
import com.example.puconnect.domain.use_cases.AuthenticationUseCases.FirebaseSignIn
import com.example.puconnect.domain.use_cases.AuthenticationUseCases.FirebaseSignOut
import com.example.puconnect.domain.use_cases.AuthenticationUseCases.FirebaseSignUp
import com.example.puconnect.domain.use_cases.AuthenticationUseCases.isUserAuthenticated
import com.example.puconnect.domain.use_cases.EventUseCases.EventUseCases
import com.example.puconnect.domain.use_cases.EventUseCases.GetAllEvents
import com.example.puconnect.domain.use_cases.EventUseCases.UploadEvent
import com.example.puconnect.domain.use_cases.MessageUseCases.GetAllConversations
import com.example.puconnect.domain.use_cases.MessageUseCases.GetMessagesByUserId
import com.example.puconnect.domain.use_cases.MessageUseCases.MessageUseCases
import com.example.puconnect.domain.use_cases.MessageUseCases.SendMessageByUserId
import com.example.puconnect.domain.use_cases.NetworkUseCases.GetAllUsers
import com.example.puconnect.domain.use_cases.NetworkUseCases.NetworkUseCases
import com.example.puconnect.domain.use_cases.PostUseCases.GetMessagesByPost
import com.example.puconnect.domain.use_cases.PostUseCases.GetPostByGuild
import com.example.puconnect.domain.use_cases.PostUseCases.GetPosts
import com.example.puconnect.domain.use_cases.PostUseCases.PostUseCases
import com.example.puconnect.domain.use_cases.PostUseCases.UploadMessage
import com.example.puconnect.domain.use_cases.PostUseCases.UploadPost
import com.example.puconnect.domain.use_cases.SkillsUseCases.GetSkills
import com.example.puconnect.domain.use_cases.SkillsUseCases.SkillsUseCases
import com.example.puconnect.domain.use_cases.UserUseCases.GetImage
import com.example.puconnect.domain.use_cases.UserUseCases.GetUserDetails
import com.example.puconnect.domain.use_cases.UserUseCases.GetUserDetailsOnce
import com.example.puconnect.domain.use_cases.UserUseCases.SetSkills
import com.example.puconnect.domain.use_cases.UserUseCases.SetUserDetails
import com.example.puconnect.domain.use_cases.UserUseCases.UpdateUserData
import com.example.puconnect.domain.use_cases.UserUseCases.UploadImage
import com.example.puconnect.domain.use_cases.UserUseCases.UserUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PuconnectModule {

    @Singleton
    @Provides
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthenticationRepository {
        return AuthenticationRepositoryImplementation(auth, firestore)
    }

    @Singleton
    @Provides
    fun provideAuthUseCases(repositoryImplementation: AuthenticationRepositoryImplementation) =
        AuthenticationUserCases(
            isUserAuthenticated = isUserAuthenticated(repositoryImplementation),
            firebaseAuthState = FirebaseAuthState(repositoryImplementation),
            firebaseSignIn = FirebaseSignIn(repositoryImplementation),
            firebaseSignOut = FirebaseSignOut(repositoryImplementation),
            firebaseSignUp = FirebaseSignUp(repositoryImplementation)
        )

    @Singleton
    @Provides
    fun provideUserRepository(
        firestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): UserRepository {
        return UserRepositoryImpl(firestore, firebaseStorage)
    }

    @Singleton
    @Provides
    fun provideUserUseCases(userRepository: UserRepository) =
        UserUseCases(
            getUserDetails = GetUserDetails(userRepository),
            getUserDetailsOnce = GetUserDetailsOnce(userRepository),
            setUserDetails = SetUserDetails(userRepository),
            setSkills = SetSkills(userRepository),
            updateUserData = UpdateUserData(userRepository),
            uploadImage = UploadImage(userRepository),
            getImage = GetImage(userRepository)
        )

    @Singleton
    @Provides
    fun provideSkillsRepository(firestore: FirebaseFirestore): SkillsRepository {
        return SkillsRepositoryImpl(firestore)
    }

    @Singleton
    @Provides
    fun provideSkillsUseCases(skillsRepositoryImpl: SkillsRepositoryImpl) =
        SkillsUseCases(
            getSkills = GetSkills(skillsRepositoryImpl),
            setSkills = com.example.puconnect.domain.use_cases.SkillsUseCases.SetSkills(
                skillsRepositoryImpl
            )
        )

    @Singleton
    @Provides
    fun providePostRepository(firestore: FirebaseFirestore): PostRepository {
        return PostRepositoryImpl(firestore)
    }

    @Singleton
    @Provides
    fun providePostUseCases(postRepositoryImpl: PostRepositoryImpl) =
        PostUseCases(
            getPosts = GetPosts(repository = postRepositoryImpl),
            uploadPost = UploadPost(repository = postRepositoryImpl),
            getPostByGuild = GetPostByGuild(repository = postRepositoryImpl),
            getMessagesByPost = GetMessagesByPost(repository = postRepositoryImpl),
            uploadMessage = UploadMessage(repository = postRepositoryImpl)
        )

    @Singleton
    @Provides
    fun provideNetworkRepository(firestore: FirebaseFirestore): NetworkRepository {
        return NetworkRepositoryImpl(firestore)
    }

    @Singleton
    @Provides
    fun provideNetworkUseCases(networkRepository: NetworkRepository) =
        NetworkUseCases(
            getAllUsers = GetAllUsers(repository = networkRepository)
        )

    @Singleton
    @Provides
    fun provideEventRepository(firestore: FirebaseFirestore): EventRepository {
        return EventRepositoryImpl(firestore)
    }

    @Singleton
    @Provides
    fun provideEventUseCases(eventRepository: EventRepository) =
        EventUseCases(
            getAllEvents = GetAllEvents(eventRepository),
            uploadEvent = UploadEvent(eventRepository)
        )

    @Singleton
    @Provides
    fun provideMessageRepository(firestore: FirebaseFirestore): MessageRepository {
        return MessageRepositoryImpl(firestore)
    }

    @Singleton
    @Provides
    fun provideMessageUseCases(messageRepository: MessageRepository) =
        MessageUseCases(
            getAllConversations = GetAllConversations(messageRepository),
            getMessagesByUserId = GetMessagesByUserId(messageRepository),
            sendMessageByUserId = SendMessageByUserId(messageRepository)
        )
}