/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author 10070128
 */
public enum EstadoEnum {
    //Coordenador -> Professor
    ANALISE {
        @Override
        public String toString(){
            return "Em análise";
        }
    },
    //Coordenador -> CRE
    DEFERIDO {
        @Override
        public String toString() {
            return "Deferido";
        }
    },
    //Coordenador -> CRE
    INDEFERIDO {
        @Override
        public String toString() {
            return "Indeferido";
        }
    },
    //CRE -> Coordenador
    PRE_ANALISE {
        @Override
        public String toString() {
            return "Em pré-análise";
        }
    },
    //Professor -> Coordenador
    APROVADO {
        @Override
        public String toString() {
            return "Aprovado";
        }
    },
    //Professor -> Coordenador
    REPROVADO {
        @Override
        public String toString() {
            return "Reprovado";
        }
    },
    //Aluno -> CRE
    ENTREGUE {
        @Override
        public String toString() {
            return "Entregue";
        }
    },
    //Professor -> Aluno
    PROVA {
        @Override
        public String toString() {
            return "Aguardando Prova";
        }
    }
}
