package com.example.notes.modal

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider: RemoteDataProvider {

    companion object {
        private val TAG = "${FireStoreProvider:: class.java.simpleName}: "
    }

    private val db = FireBaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.addSnapshotListener(object: EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?){
                if (error != null){
                    result.value = NoteResult.Error(error)
                } else if (value != null){
                    val notes = mutableListOf<Note>()

                    for (doc: QueryDocumentsSnapshot in value){
                        notes.add(doc.toObject(Note::class.java))
                    }
                    result.value = NoteResult.Success(notes)
                }
            }
        })
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(id)
            .get()
            .addOnSuccessListener( snapshot ->
                result.value = NoteResult.Success(snapshot.toObject(Note::class.java))
                }
            .addOnFailureListener { exception ->
                result.value = NoteResult.Error(exception)
            }

        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(note.id)
            .set(note)
            .addOnSuccesListener{
                     Log.d(TAG, "Note $note is saved")
                     result.value = NoteResult.Success(note)
                    }.addOnFailureListener{
                        OnFailureListener { exeption ->
                               Log.d(TAG, "Error saving note $note, message: ${exeption.message}")
                               result.value= NoteResult.Error(exeption)
                        }
                 }

        return result
      }
}