package com.programa;

public class Questionario {

    private String questao;
    private int cont;
    public static String curso;
    private String[] questoes = {"Gostaria de trabalhar com sistemas diversos como sofwares para smart tvs, impressoras, carros, aviões e sondas espaciais.", "Gostaria de trabalhar com desenvolvimento de software para web e/ou aplicativos de celulares",
    "Gostaria de trabalhar com desenvolvimento de jogos para video games","Gostaria de trabalhar na área acadêmica - Pesquisa cientifíca, lecionar.", "Gostaria de trabalhar gerenciando projetos de TI",
    "Gostaria de trabalhar na análise de soluções para empresas em equipes com pessoas de diversas áreas"};
    private Curso[] cursos = {new Curso("Ciência da Computação"), new Curso("Engenharia de Computação"), new Curso("Sistemas de Informação"), new Curso("Engenharia de Software")};


    public Questionario(){
        cont = 0;
    }
    private class Curso{
        private String nome;
        private double pont;

        public Curso ( String nome){
            this.nome = nome;
            pont = 0;
        }

        public void maisPont(double pont){
            this.pont+= pont;
        }

        public double getPont(){
            return pont;
        }

        public String getNome(){
            return this.nome;

        }
    }

    public String getPergunta(){
        if(cont <6 ) { cont++; return questoes[cont-1];}
        return null;
    }

    public String getCurso(){
        double po = 0;
        String opc = "Nenhum Selecionado";
        for (Curso c : cursos) {
            if (c.getPont() > po){
                opc = c.getNome();
                po = c.getPont();
            }
        }
        return opc;
    }

    public void daPonto(boolean resp)
    {
        if(resp){
            switch(cont){
                case 1:
                    cursos[1].maisPont(1.5);
                    cursos[0].maisPont(0.5);
                    break;
                case 2:
                    cursos[0].maisPont(1);
                    cursos[2].maisPont(0.5);
                    cursos[3].maisPont(1);
                    break;
                case 3:
                    cursos[0].maisPont(1.5);
                    cursos[3].maisPont(1);
                    break;
                case 4:
                    cursos[0].maisPont(1);
                    cursos[1].maisPont(1.5);
                    break;
                case 5:
                    cursos[0].maisPont(0.5);
                    cursos[2].maisPont(1);
                    cursos[3].maisPont(1.5);
                    break;
                case 6:
                    cursos[2].maisPont(2);
                    cursos[3].maisPont(1);
                    break;
                default: break;
            }
        }
    }
}
