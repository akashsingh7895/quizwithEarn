package com.avssolution.akashsingh.quizearnxyz;

import static com.avssolution.akashsingh.quizearnxyz.MainActivity.InterID;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.avssolution.akashsingh.quizearnxyz.Model.Question;

import com.avssolution.akashsingh.quizearnxyz.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.unity3d.ads.UnityAds;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements MaxAdListener {

    ActivityQuizBinding binding;

    ArrayList<Question> questions;
    int index = 0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswers = 0;

    public static Dialog loadingDialog;

    //applovin ads
    private MaxInterstitialAd interstitialAd;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


       questions = new ArrayList<>();
       database = FirebaseFirestore.getInstance();

       String categoryName = getIntent().getStringExtra("catName");
       binding.toolText.setText(categoryName + " quiz");


        interstitialAd = new MaxInterstitialAd(getString(R.string.inter),this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();
        // loadnetiveAd();


        ///loading Dialog
        loadingDialog = new Dialog(QuizActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////end loading dialog

//

        final String catId = getIntent().getStringExtra("catId");

        Random random = new Random();
        final int rand = random.nextInt(5);

        database.collection("categories")
                .document(catId)
                .collection("question")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.getDocuments().size() < 5) {

                    database.collection("categories")
                            .document(catId)
                            .collection("question")
                            .whereLessThanOrEqualTo("index", rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                                loadingDialog.dismiss();
                            }
                            setNextQuestion();
                        }
                    });
                } else {
                    for(DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                        loadingDialog.dismiss();
                    }
                    setNextQuestion();
                }
            }
        });

        resetTimer();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finish();
                Intent intent = new Intent(QuizActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        binding.quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityAds.show(QuizActivity.this,InterID);
                startActivity(new Intent(QuizActivity.this,CategoryActivity.class));
            }
        });


//      questions.add(new Question("what is food","car","train","bus","apple","apple"));
//      questions.add(new Question("what is vechle","car","banana","carrot","apple","car"));

    }


    void resetTimer(){
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                index++;
                setNextQuestion();
            }
        };
    }

    void showAnswer(){
        if (question.getAnswer().equals(binding.option1.getText().toString())){
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else if (question.getAnswer().equals(binding.option2.getText().toString())){
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else if (question.getAnswer().equals(binding.option3.getText().toString())){
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else if(question.getAnswer().equals(binding.option4.getText().toString())){
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
        }

    }

    void checkAnswer(TextView textView){

        String selectedAnswer = textView.getText().toString();

        if (selectedAnswer.equals(question.getAnswer())){
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
            correct();
            correctAnswers++;
        }else {
            showAnswer();
            inCorrect();
            textView.setBackground(getResources().getDrawable(R.drawable.wrong_option));
        }

    }


    void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    void setNextQuestion(){

        if (timer !=null){
            timer.cancel();
        }
        timer.start();

        if (index<questions.size()){
            binding.questionCounter.setText(String.format("%d/%d",(index+1),questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
        }
    }



    public void onClick(View view){
        switch (view.getId()) {

            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:


                if (timer !=null){
                    timer.cancel();
                }

                setClickable(false);

                TextView selected = (TextView) view;
                checkAnswer(selected);
                    break;
            case R.id.btn_next:
                if (index < questions.size()) {
                    reset();
                    index++;
                    setNextQuestion();
                    setClickable(true);
                    break;
                }else {
                    if (interstitialAd.isReady()){
                        interstitialAd.showAd();
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        intent.putExtra("correct", correctAnswers);
                        intent.putExtra("total", questions.size());
                        startActivity(intent);
                        Toast.makeText(this, "Quiz Finished.", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                        intent.putExtra("correct", correctAnswers);
                        intent.putExtra("total", questions.size());
                        startActivity(intent);
                        Toast.makeText(this, "Quiz Finished.", Toast.LENGTH_SHORT).show();
                    }

                }
        }
    }

    void correct(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.correct);
        mediaPlayer.start();
    }

    void inCorrect(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.wrongt);
        mediaPlayer.start();
    }

    public void setClickable(boolean b){
        binding.option1.setClickable(b);
        binding.option2.setClickable(b);
        binding.option3.setClickable(b);
        binding.option4.setClickable(b);
    }

    @Override
    public void onAdLoaded(MaxAd ad) {

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {

    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }
}