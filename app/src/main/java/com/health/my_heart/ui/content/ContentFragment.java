package com.health.my_heart.ui.content;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.health.my_heart.R;
import com.health.my_heart.activity.Diets;
import com.health.my_heart.activity.FoodActivity;
import com.health.my_heart.activity.Heart_diseases;
import com.health.my_heart.activity.SportActivity;
import com.health.my_heart.databinding.FragmentArticlesBinding;
import com.health.my_heart.domain.model.ContentType;

import java.util.ArrayList;

public class ContentFragment extends Fragment implements OnCardClickListener {
    private FragmentArticlesBinding binding;
    private ContentAdapter adapter;
    CardView card_view1, card_view2, card_view3, card_view4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles,
                container, false);

        card_view1 = (CardView) view.findViewById(R.id.card_view1);
        card_view1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Heart_diseases.class);
                getActivity().startActivity(intent);
            }
        });

        card_view2 = (CardView) view.findViewById(R.id.card_view2);
        card_view2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Diets.class);
                getActivity().startActivity(intent);
            }
        });

        card_view3 = (CardView) view.findViewById(R.id.card_view3);
        card_view3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), SportActivity.class);
                getActivity().startActivity(intent);
            }
        });
        card_view4 = (CardView) view.findViewById(R.id.card_view4);
        card_view4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void onCardClickListener(ContentCard card) {
        if (card.getContentType() != ContentType.FOOD) {
            ContentFragmentDirections.ActionOpenCategories actionOpenCategories = ContentFragmentDirections.actionOpenCategories(card.getContentType());
            Navigation.findNavController(binding.getRoot()).navigate(actionOpenCategories);
        } else {
            openFoodFragment();
        }
    }

    private void openFoodFragment() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.destination_food);
    }

    private void initRecyclerView() {
        adapter = new ContentAdapter(this);
//        binding.articlesRv.setAdapter(adapter);
//        mockAdapter();
    }

    private void mockAdapter() {
        ArrayList<ContentCard> list = new ArrayList<>();
        list.add(new ContentCard("Болезни сердца", ContentType.HEART_DISEASES, R.drawable.bg_heart_diseases));
        list.add(new ContentCard("Диеты", ContentType.DIETS, R.drawable.bg_diets));
        list.add(new ContentCard("Физические нагрузки", ContentType.SPORT, R.drawable.bg_sport));
        list.add(new ContentCard("Полезные продукты", ContentType.FOOD, R.drawable.bg_healthy_food));
        adapter.setData(list);
    }
}
