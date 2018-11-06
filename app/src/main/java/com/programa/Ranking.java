    package com.programa;

    import android.support.annotation.NonNull;

    import java.util.ArrayList;

    public class Ranking{
        private int pontos;
        private String email;

        public Ranking( String email, int pontos) {
            this.pontos = pontos;
            this.email = email;
        }

        public int getPontos() {
                return pontos;
            }

            public void setPontos(int pontos) {
                this.pontos = pontos;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            @Override
            public String toString(){
                return "Email: " + email + " Pontos: " + pontos;
            }
    }
