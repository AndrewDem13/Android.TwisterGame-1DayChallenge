package com.example.admin.twister;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class GameActivity extends AppCompatActivity {

    int usersCount;
    Game game;
    Button next, fall;
    TextView players, currentPlayer;
    Listener listener = new Listener();
    boolean firstLaunch = true;
    Toast toast;
    Intent intent;
    ImageView animation1, animation2;
    AnimationDrawable mAnimation1, mAnimation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Получаем выбранное ранее кол-во игроков, начинаем игру
        usersCount = getIntent().getExtras().getInt("numberOfPlayers");
        game = new Game(usersCount, this);

        // Табло кол-ва игроков
        players = (TextView) findViewById(R.id.playersCount);
        players.setText(String.valueOf(usersCount));

        // Тобло-указатель текущего игрока
        currentPlayer = (TextView) findViewById(R.id.currentPlayer);

        // Инициализация главных игровых кнопок
        // Ход для следующего игрока
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(listener);
        // Текущий игрок не справился и упал
        fall = (Button) findViewById(R.id.fall);
        fall.setOnClickListener(listener);

        // Место для анимации
        animation1 = (ImageView) findViewById(R.id.animation1);
        animation2 = (ImageView) findViewById(R.id.animation2);

        // Создаем анимацию
        mAnimation1 = new AnimationDrawable();
        mAnimation1.setOneShot(true);
        mAnimation2 = new AnimationDrawable();
        mAnimation2.setOneShot(true);
        mAnimation2.setAlpha(185);
        for (int i = 0; i < 3; i++)
        {
            mAnimation1.addFrame((BitmapDrawable)getDrawable(R.drawable.red), 100);
            mAnimation1.addFrame((BitmapDrawable)getDrawable(R.drawable.yellow), 100);
            mAnimation1.addFrame((BitmapDrawable)getDrawable(R.drawable.blue), 100);
            mAnimation1.addFrame((BitmapDrawable)getDrawable(R.drawable.green), 100);

            mAnimation2.addFrame((BitmapDrawable)getDrawable(R.drawable.right_hand), 100);
            mAnimation2.addFrame((BitmapDrawable)getDrawable(R.drawable.right_leg), 100);
            mAnimation2.addFrame((BitmapDrawable)getDrawable(R.drawable.left_hand), 100);
            mAnimation2.addFrame((BitmapDrawable)getDrawable(R.drawable.left_leg), 100);
        }
    }

    // Конец игры, переход на финальный экран
    private void goCongrats() {
        intent = new Intent(this, CongratsActivity.class);
        intent.putExtra("winner", game.getWinner());
        intent.putExtra("losers", game.getLosers());
        startActivity(intent);
        this.finish();
    }

    // Запуск анимации и вспомогательные методы
    public void doAnimation() {
        mAnimation1.stop();
        mAnimation2.stop();
        mAnimation1.addFrame(getRandomColor(), 1);
        mAnimation2.addFrame(getRandomBodypart(), 1);
        mAnimation1.start();
        mAnimation2.start();
    }
    private Drawable getRandomColor() {
        switch (game.getRandom()) {
            case 0:
                return (BitmapDrawable)getDrawable(R.drawable.yellow);
            case 1:
                return (BitmapDrawable)getDrawable(R.drawable.green);
            case 2:
                return (BitmapDrawable)getDrawable(R.drawable.red);
            default:
                return (BitmapDrawable)getDrawable(R.drawable.blue);
        }
    }
    private Drawable getRandomBodypart() {
        switch (game.getRandom()) {
            case 0:
                return (BitmapDrawable)getDrawable(R.drawable.left_hand);
            case 1:
                return (BitmapDrawable)getDrawable(R.drawable.left_leg);
            case 2:
                return (BitmapDrawable)getDrawable(R.drawable.right_leg);
            default:
                return (BitmapDrawable)getDrawable(R.drawable.right_hand);
        }
    }

    public class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // Игрок упал и выбывает из игры
                case R.id.fall:
                    // Если все еще есть кому играть
                    if (game.failAction()) {
                        players.setText(String.valueOf(game.getUsersCount()));
                        // TODO Toast message "player ID loose"
                        toast = Toast.makeText(getApplicationContext(), String.format(Locale.getDefault(), "Игрок %d проиграл :(", game.getCurrent().getId()), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else {
                        // TODO Toast Game over
                        toast = Toast.makeText(getApplicationContext(), "Game over!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        // TODO Winner congratulations
                        goCongrats();
                        break;
                    }
                    currentPlayer.setText(String.format(Locale.getDefault(), "Игрок %d", game.getCurrent().getId()));
                    players.setText(String.valueOf(game.getUsersCount()));
                    fall.setClickable(false);
                    break;

                // Все ОК, ход для следующего игрока
                case R.id.next:
                    if (firstLaunch) {
                        animation1.setBackground(mAnimation1);
                        animation2.setBackground(mAnimation2);
                        mAnimation1.setVisible(true, true);
                        mAnimation2.setVisible(true, true);
                        fall.setVisibility(View.VISIBLE);
                        next.setText("Дальше");
                        firstLaunch = false;
                    }
                    game.nextAction();
                    fall.setClickable(true);
                    currentPlayer.setText(String.format(Locale.getDefault(), "Игрок %d", game.getCurrent().getId()));
                    break;
            }
        }
    }
}
