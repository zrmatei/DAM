package eu.ase.ro.damapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import eu.ase.ro.damapp.model.DateConverter;
import eu.ase.ro.damapp.model.Expense;

public class AddExpenseActivity extends AppCompatActivity {
    public static final String EXPENSE_KEY = "expense_key";
    public static final String EXPENSE_POS = "expense_position";
    private int pos = -1;

    private TextInputEditText tietDate;
    private TextInputEditText tietAmount;
    private TextInputEditText tietDescription;
    private Spinner spnCategory;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponents();
        intent = getIntent();

        if (intent != null && intent.hasExtra(EXPENSE_KEY)) {
            Expense expense = intent.getParcelableExtra(EXPENSE_KEY);
            if (expense != null) {
                popExpense(expense);
            }
            pos = intent.getIntExtra(EXPENSE_POS, -1);
        }
    }


    private void popExpense(Expense expense) {
        tietDate.setText(DateConverter.fromDate(expense.getDate()));
        tietAmount.setText(String.valueOf(expense.getAmount()));
        tietDescription.setText(expense.getDescription());
    }

    private void initComponents() {
        tietDate = findViewById(R.id.add_tiet_date);
        tietAmount = findViewById(R.id.add_tiet_amount);
        tietDescription = findViewById(R.id.add_tiet_description);
        spnCategory = findViewById(R.id.add_spn_category);
        btnSave = findViewById(R.id.add_btn_save);
        btnSave.setOnClickListener(getSaveEvent());
    }

    private View.OnClickListener getSaveEvent() {
        return v -> {
            if (isValid()) {
                Expense expense = buildFromView();
                //pt modif obj
                Intent intent = new Intent();
                intent.putExtra(EXPENSE_KEY, expense);
                intent.putExtra(EXPENSE_POS, pos);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
    }

    private Expense buildFromView() {
        Date date = DateConverter.toDate(tietDate.getText().toString());
        double amount = Double.parseDouble(tietAmount.getText().toString());
        String category = (String) spnCategory.getSelectedItem();
        String description = tietDescription.getText().toString();

        return new Expense(date, amount, category, description);
    }

    private boolean isValid() {
        if (tietDate.getText() == null
                || DateConverter.toDate(tietDate.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_invalid_date,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (tietAmount.getText() == null ||
                tietAmount.getText().toString().trim().isEmpty()
                || Double.parseDouble(tietAmount.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_invalid_amount,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}