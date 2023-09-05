package com.example.puconnect.di

import com.example.puconnect.data.AuthenticationRepositoryImplementation
import com.example.puconnect.domain.repository.AuthenticationRepository
import com.example.puconnect.domain.use_cases.AuthenticationUserCases
import com.example.puconnect.domain.use_cases.FirebaseAuthState
import com.example.puconnect.domain.use_cases.FirebaseSignIn
import com.example.puconnect.domain.use_cases.FirebaseSignOut
import com.example.puconnect.domain.use_cases.FirebaseSignUp
import com.example.puconnect.domain.use_cases.isUserAuthenticated
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
    fun provideFirebaseAuthentication():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
    @Singleton
    @Provides
    fun provideFirebaseFirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage():FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(auth: FirebaseAuth,firestore: FirebaseFirestore):AuthenticationRepository{
        return AuthenticationRepositoryImplementation(auth,firestore)
    }

    @Singleton
    @Provides
    fun provideAuthUseCases(repositoryImplementation: AuthenticationRepositoryImplementation)=AuthenticationUserCases(
        isUserAuthenticated = isUserAuthenticated(repositoryImplementation),
        firebaseAuthState = FirebaseAuthState(repositoryImplementation),
        firebaseSignIn = FirebaseSignIn(repositoryImplementation),
        firebaseSignOut = FirebaseSignOut(repositoryImplementation),
        firebaseSignUp = FirebaseSignUp(repositoryImplementation)
    )
}