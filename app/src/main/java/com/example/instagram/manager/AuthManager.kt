package com.example.instagram.manager

import com.example.instagram.manager.handler.AuthHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthManager {
    companion object{
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        fun isSignedIn(): Boolean{
            return currentUser() != null
        }

        fun currentUser(): FirebaseUser? {
            return firebaseAuth.currentUser
        }


        fun signIn(email: String, password: String, handler: AuthHandler){
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val uid = currentUser()?.uid
                    handler.onSuccess(uid!!)
                }else {
                    handler.onError(task.exception)
                }
            }
        }

        fun signUp(email: String, password: String, handler: AuthHandler){
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val uid = currentUser()?.uid
                    handler.onSuccess(uid!!)
                }else {
                    handler.onError(task.exception!!)
                }
            }
        }

        fun signOut(){
            firebaseAuth.signOut()
        }
    }
}