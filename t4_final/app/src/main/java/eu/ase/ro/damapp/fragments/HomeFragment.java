package eu.ase.ro.damapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.damapp.AddExpenseActivity;
import eu.ase.ro.damapp.MainActivity;
import eu.ase.ro.damapp.R;
import eu.ase.ro.damapp.model.Expense;
import eu.ase.ro.damapp.model.ExpenseAdapter;

public class HomeFragment extends Fragment {

    private static final String ARGS_EXPENSES = "args_expenses";
    private List<Expense> expenses;

    private ListView lvExpenses;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(
            List<Expense> expenses) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        //adaugam params.
        bundle.putParcelableArrayList(ARGS_EXPENSES,
                (ArrayList<? extends Parcelable>) expenses);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //citesc date din bundle
            expenses = getArguments()
                    .getParcelableArrayList(ARGS_EXPENSES);
            Log.i("HomeFragment", expenses.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("HomeFragmentView", expenses.toString());
        View view = inflater
                .inflate(R.layout.fragment_home,
                        container, false);
        if (getContext() != null) {
            lvExpenses = view.findViewById(R.id.home_lv_expenses);
//            ArrayAdapter<Expense> adapter = new ArrayAdapter<>(
//                    getContext().getApplicationContext(),
//                    android.R.layout.simple_list_item_1,
//                    expenses);

            ExpenseAdapter adapter = new ExpenseAdapter(
                    getContext().getApplicationContext(),
                    R.layout.lv_row,
                    expenses,
                    getLayoutInflater()
            );
            lvExpenses.setAdapter(adapter);

            //click lv event
            lvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Expense selected = expenses.get(position);
                    Intent intent = new Intent(getContext(), AddExpenseActivity.class);
                    intent.putExtra(AddExpenseActivity.EXPENSE_KEY, selected);

                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).editExpense(position);
                    }
                }

            });

            lvExpenses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Expense selected = expenses.get(position);
                    expenses.remove(selected);
                    Toast.makeText(getContext(), R.string.obiect_sters, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    return true;
                }
            });
        }
        return view;
    }

    public void notifyAdapter() {
        ExpenseAdapter adapter = (ExpenseAdapter) lvExpenses.getAdapter();
        adapter.notifyDataSetChanged();
    }


}