/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Thiago
 */
public enum MensagemEnum {
    //Sistema -> Professor
    ANALISE {
        @Override
        public String toString(){
            return "Uma nova Solicitação pendente de analise. Protocolo da solicitação: ";
        }
    },
    //Sistema -> CRE
    DEFERIDO {
        @Override
        public String toString() {
            return "Solicitação deferida pelo coordenador. Protocolo da solicitação: ";
        }
    },
    //Sistema -> CRE
    INDEFERIDO {
        @Override
        public String toString() {
            return "Solicitação indeferida pelo coordenador. Protocolo da solicitação: ";
        }
    },
    //Sistema -> Coordenador
    PRE_ANALISE {
        @Override
        public String toString() {
            return "Uma nova Solicitação pendente para analise. Protocolo da solicitação: ";
        }
    },
    //Sistema -> Coordenador
    APROVADO {
        @Override
        public String toString() {
            return "Solicitação aprovada pelo professor. Protocolo da solicitação: ";
        }
    },
    //Sistema -> Coordenador
    REPROVADO {
        @Override
        public String toString() {
            return "Solicitação reprovada pelo Professor. Protocolo da solicitação: ";
        }
    },
    //Sistema -> CRE
    ENTREGUE_CRE {
        @Override
        public String toString() {
            return "Foi criada uma nova Solicitação. Protocolo da solocitação: ";
        }
    },
    //Sistema -> Aluno
    ENTREGUE_ALUNO{
        @Override
        public String toString() {
            return "Sua Solicitação foi entregue para a Coordenação de Registros Escolares. Seu Protocolo é: ";
        }
    },
    //Sistema -> Aluno
    PROVA_ALUNO {
        @Override
        public String toString() {
            return "Documentação aprovada pelo professor, o mesmo entrará em contato para marcar a prova. Protocolo da solicitação: ";
        }
    },
    //Sistema -> Professor
    PROVA_PROFESSOR {
        @Override
        public String toString() {
            return "Entre em contato com o aluno para marcar a data da prova. Protocolo da solicitação: ";
        }
    }
}
