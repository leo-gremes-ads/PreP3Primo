package br.edu.fateczl.prep3primo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.InputMismatchException;

public class MainActivity extends AppCompatActivity {

    // Declaração dos elementos utilizados
    private ConstraintLayout main;
    private EditText et_digitaNumero;
    private TextView label_resposta;
    private ImageView img_viewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // instancia dos elementos
        main = findViewById(R.id.main);
        et_digitaNumero = findViewById(R.id.et_digitaNumero);
        label_resposta = findViewById(R.id.label_resposta);
        Button btn_verificar = findViewById(R.id.btn_verificar);
        img_viewer = findViewById(R.id.img_viewer);

        // função do botão verificar e resetar a cor quando focar no campo de editar texto
        btn_verificar.setOnClickListener(op -> exibir());
        et_digitaNumero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    main.setBackgroundResource(R.drawable.default_bg_gradient);
            }
        });
    }

    private void exibir() {
        et_digitaNumero.clearFocus();
        String resposta;
        int num = 0;

        // verificação se o campo está vazio
        try {
            num = Integer.parseInt(et_digitaNumero.getText().toString());
        } catch (Exception e) {
            resposta = getString(R.string.naoPositivo);
            label_resposta.setText(resposta);
            et_digitaNumero.setText("");
        }

        // verificação de números negativos ou 0
        if (num < 1) {
            resposta = getString(R.string.naoPositivo);
            main.setBackgroundResource(R.drawable.red_light_gradient);
            img_viewer.setVisibility(View.INVISIBLE);
        } else {
            boolean primo = ehPrimo(num);
            if (primo) {
                resposta = num + " " + getString(R.string.ehPrimo); // -> define a string
                main.setBackgroundResource(R.drawable.green_light_gradient); // -> muda a cor do fundo
                img_viewer.setImageResource(R.drawable.sim_primo); // -> atualiza o source da imagem
                img_viewer.setVisibility(View.VISIBLE); // -> exibe a imagem
            } else {
                resposta = num + " " + getString(R.string.negativo);
                main.setBackgroundResource(R.drawable.blue_light_gradient);
                img_viewer.setImageResource(R.drawable.nao_primo);
                img_viewer.setVisibility(View.VISIBLE);
            }
        }
        label_resposta.setText(resposta);
        et_digitaNumero.setText("");
    }

    private boolean ehPrimo(int num) {
        // casos especiais
        if (num == 1) return false;

        int i = 2;
        // verifica todos os números até a raiz quadrada do alvo
        // caso encontre alguma divisão de resto 0, retorna falso pois o número nao é primo
        while (i <= num / i) {
            if (num % i++ == 0)
                return false;
        }
        return true;
    }
}