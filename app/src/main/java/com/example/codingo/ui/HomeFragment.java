package com.example.codingo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.codingo.BaseActivity;
import com.example.codingo.Model.Quote;
import com.example.codingo.QuoteService;
import com.example.codingo.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private ImageButton mQuizButton, mFlashcardButton, mVideoButton;
    private CardView mCard;
    private TextView mQuote, mAuthor;
    private ProgressBar mProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  root = inflater.inflate(R.layout.fragment_home, container, false);
        mQuizButton = root.findViewById(R.id.ib_quiz);
        mFlashcardButton = root.findViewById(R.id.ib_flashcards);
        mVideoButton = root.findViewById(R.id.ib_video);
        mCard = root.findViewById(R.id.cv_quote);
        mQuote = root.findViewById(R.id.tv_quote);
        mAuthor = root.findViewById(R.id.tv_author);
        mProgress = root.findViewById(R.id.pb_quote);

        mQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchQuizFragment();
            }
        });

        mFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLearnToolFragment("Flashcards");
            }
        });

        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLearnToolFragment("Videos");
            }
        });

        new GetQuoteTask().execute();
        return root;
    }

    public class GetQuoteTask extends AsyncTask<Void, Void, Quote> {
        @Override
        protected void onPreExecute() {
            mQuote.setText("");
            mAuthor.setText("");
            mCard.setOnClickListener(null);
            mProgress.setIndeterminate(true);
            mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Quote doInBackground(Void... voids) {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://programming-quotes-api.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                QuoteService service = retrofit.create(QuoteService.class);
                Call<Quote> quoteCall = service.getQuote();
                Response<Quote> quoteResponse = quoteCall.execute();
                return quoteResponse.body();
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Quote quote) {
            mProgress.setVisibility(View.INVISIBLE);
            mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new GetQuoteTask().execute();
                }
            });
            if(quote != null) {
                mQuote.setText("\""+quote.getEn()+"\"");
                mAuthor.setText("- "+quote.getAuthor());
            }
            else {
                mQuote.setText("Network issue. Quote failed to load.");
            }
        }
    }

    private void launchQuizFragment() {
        if(getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_quiz);
        }
    }

    private void launchLearnToolFragment(String type) {
        if(getActivity() instanceof BaseActivity) {
            getActivity().getIntent().putExtra("LEARN_TYPE", type);
            ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_learn);
            ((BaseActivity) getActivity()).getNavController().navigate(R.id.navigation_learn_topic);
        }
    }
}
