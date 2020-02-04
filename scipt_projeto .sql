CREATE DATABASE teste_projeto;
USE teste_projeto;

DROP TABLE IF EXISTS avaliador CASCADE;
CREATE TABLE avaliador
(cod_avaliador INTEGER AUTO_INCREMENT PRIMARY KEY,
 cpf_avaliador CHAR(14) NOT NULL,
 rg_avaliador CHAR(12) NOT NULL,
 nome_avaliador VARCHAR(50) NOT NULL,
 sexo CHAR(1) NOT NULL,
 data_nasct CHAR(10) NOT NULL,
 nivel_academico VARCHAR(15) NOT NULL,
 local_trab VARCHAR(100) NOT NULL);
 ALTER TABLE avaliador AUTO_INCREMENT = 40001;
                        
 DROP TABLE IF EXISTS area_pesquisa CASCADE;
 CREATE TABLE area_pesquisa 
(cod_especifica CHAR(6) PRIMARY KEY,
 nome_especifica VARCHAR(50) NOT NULL,
 cod_area CHAR(6) NOT NULL,
 nome_area VARCHAR(70) NOT NULL);
 
DROP TABLE IF EXISTS solicitante CASCADE;
CREATE TABLE solicitante
(cpf_solicitante CHAR(14) PRIMARY KEY,
 rg_solicitante CHAR(12) NOT NULL,
 nome_solicitante VARCHAR(50) NOT NULL,
 sexo CHAR(1) NOT NULL,
 data_nasc CHAR(10) NOT NULL, 
 nivel_academico VARCHAR(15) NOT NULL,
 local_graduacao VARCHAR(100)NOT NULL);
                               
 DROP TABLE IF EXISTS projeto CASCADE;
 CREATE TABLE projeto 
 (cod_projeto INTEGER auto_increment  PRIMARY KEY,
  cod_curso CHAR(12) NOT NULL,
  cpf_solicitante CHAR(14) NOT NULL,
  instituicao VARCHAR(100) NOT NULL,
  duracao_meses INT NOT NULL,  
  orcamento DOUBLE NOT NULL,
  titulo VARCHAR(60) NOT NULL,
  FOREIGN KEY(cpf_solicitante)
  REFERENCES solicitante(cpf_solicitante)
  ON DELETE CASCADE ON UPDATE CASCADE, 
  FOREIGN KEY (cod_curso)
  REFERENCES  area_pesquisa(cod_especifica)
  ON DELETE CASCADE ON UPDATE CASCADE);
  ALTER TABLE projeto AUTO_INCREMENT = 100001;

DROP TABLE IF EXISTS avaliacao CASCADE;
CREATE TABLE avaliacao 
(cod_avaliacao INTEGER AUTO_INCREMENT PRIMARY KEY,
 cod_interno INTEGER NOT NULL,
 cod_avaliador INTEGER NOT NULL,
 data_entrada CHAR(10) NOT NULL,
 data_saida CHAR(10),
 resposta VARCHAR(15) NOT NULL,
 FOREIGN KEY(cod_interno)
 REFERENCES projeto(cod_projeto)
 ON DELETE CASCADE ,
 FOREIGN KEY(cod_avaliador)
 REFERENCES avaliador(cod_avaliador)
 ON DELETE CASCADE ON UPDATE CASCADE);
ALTER TABLE avaliacao AUTO_INCREMENT = 80001;
  
INSERT INTO area_pesquisa VALUES
( "1001", "Matemática", "1000","Ciências exatas e da terra"),
( "1002", "Física", "1000","Ciências exatas e da terra"),
( "1003", "Química", "1000","Ciências exatas e da terra"),
( "1004", "Geociências", "1000","Ciências exatas e da terra"),
( "1005", "Oceanografia", "1000","Ciências exatas e da terra"),
( "1006", "Astronomia", "1000","Ciências exatas e da terra"),
( "1007", "Ciência da computacao", "1000","Ciências exatas e da terra"),
( "1008", "Probabilidade e Estatística", "1000","Ciências exatas e da terra"),

( "2001", "Zoologia", "2000","Ciências biologicas"),
( "2002", "Genética", "2000","Ciências biologicas"),
( "2003", "Biofísica", "2000","Ciências biologicas"),
( "2004", "Biologia Geral", "2000","Ciências biologicas"),
( "2005", "Botânica", "2000","Ciências biologicas"),
( "2006", "Ecologia", "2000","Ciências biologicas"),
( "2007", "Morfologia", "2000","Ciências biologicas"),
( "2008", "Fisiologia", "2000","Ciências biologicas"),
( "2009", "Bioquímica", "2000","Ciências biologicas"),
( "2010", "Parasitologia", "2000","Ciências biologicas"),
( "2011", "Farmacologia", "2000","Ciências biologicas"),
( "2012", "Imunologia", "2000","Ciências biologicas"),
( "2013", "Microbiologia", "2000","Ciências biologicas"),

( "3001", "Engenharia civil", "3000","Engenharias"),
( "3002", "Engenharia de Minas", "3000","Engenharias"),
( "3003", "Engenharia Mêcanica", "3000","Engenharias"),
( "3004", "Engenharia de Materiais e Metalúrgica", "3000","Engenharias"),
( "3005", "Engenharia Elétrica", "3000","Engenharias"),
( "3006", "Engenharia Química", "3000","Engenharias"),
( "3007", "Engenharia Sanitária", "3000","Engenharias"),
( "3008", "Engenharia de Produção", "3000","Engenharias"),
( "3009", "Engenharia Nuclear", "3000","Engenharias"),
( "3010", "Engenharia  de Transportes", "3000","Engenharias"),
( "3011", "Engenharia  Naval e Oceânica", "3000","Engenharias"),
( "3012", "Engenharia Aeroespacial", "3000","Engenharias"),
( "3013", "Engenharia Biomédica", "3000","Engenharias"),


( "4001", "Medicina", "4000","Ciências da Saúde"),
( "4002", "Nutrição", "4000","Ciências da Saúde"),
( "4003", "Odontologia", "4000","Ciências da Saúde"),
( "4004", "Farmácia", "4000","Ciências da Saúde"),
( "4005", "Enfermagem", "4000","Ciências da Saúde"),
( "4006", "Saúde Coletiva", "4000","Ciências da Saúde"),
( "4007", "Fonoaudiologia", "4000","Ciências da Saúde"),
( "4008", "Fisioterapia e Terapia Ocupacional", "4000","Ciências da Saúde"),
( "4009", "Educação Física", "4000","Ciências da Saúde"),

( "5001", "Agronomia", "5000","Ciências Agrárias"),
( "5002", "Zootecnia", "5000","Ciências Agrárias"),
( "5003", "Engenharia Agrícola", "5000","Ciências Agrárias"),
( "5004", "Recursos Florestais e Engenharia Florestal ", "5000","Ciências Agrárias"),
( "5005", "Medicina Veterinária", "5000","Ciências Agrárias"),
( "5006", "Recursos Pesqueiros e Engenharia de Pesca", "5000","Ciências Agrárias"),
( "5007", "Ciência e Tecnologia de Alimentos", "5000","Ciências Agrárias"),


( "6001", "Direito", "6000","Ciências Sociais Aplicadas"),
( "6002", "Admnistração", "6000","Ciências Sociais Aplicadas"),
( "6003", "Turismo", "6000","Ciências Sociais Aplicadas"),
( "6004", "Economia", "6000","Ciências Sociais Aplicadas"),
( "6005", "Arquitetura e Urbanismo", "6000","Ciências Sociais Aplicadas"),
( "6006", "Planejamento Urbano e Regional", "6000","Ciências Sociais Aplicadas"),
( "6007", "Ciência da Informação", "6000","Ciências Sociais Aplicadas"),
( "6008", "Museologia", "6000","Ciências Sociais Aplicadas"),
( "6009", "Comunicação", "6000","Ciências Sociais Aplicadas"),
( "6010", "Serviço Social", "6000","Ciências Sociais Aplicadas"),
( "6011", "Economia Doméstica", "6000","Ciências Sociais Aplicadas"),
( "6012", "Desenho Industrial", "6000","Ciências Sociais Aplicadas"),


( "7001", "Letras", "7000","Ciências Humanas"),
( "7002", "Sociologia", "7000","Ciências Humanas"),
( "7003", "Antropologia", "7000","Ciências Humanas"),
( "7004", "Arqueologia", "7000","Ciências Humanas"),
( "7005", "História", "7000","Ciências Humanas"),
( "7006", "Geografia", "7000","Ciências Humanas"),
( "7007", "Psicologia", "7000","Ciências Humanas"),
( "7008", "Educação", "7000","Ciências Humanas"),
( "7009", "Ciência Política", "7000","Ciências Humanas"),
( "7010", "Teologia", "7000","Ciências Humanas"),
( "7011", "Linguística", "7000","Ciências Humanas"),
( "7012", "Filosofia", "7000","Ciências Humanas");

INSERT INTO solicitante VALUES
("305.435.480-66", "12.345.678-9", "André", "M", "20/05/1985","mestrado","USJT"),
("102.691.420-53", "21.645.378-0", "Bruna", "F", "08/12/1990","mestrado","USP"),
("961.948.130-59", "98.636.258-6", "Carlos", "M", "30/09/1970","mestrado","UNICAMP"),
("947.133.680-59", "00.065.023-5", "Denilson", "O", "17/03/1988","Doutorado","USP"),
("482.287.210-64", "01.023.045-0", "Edson", "M", "03/01/1958","Doutorado","Mackenzie"),
("613.230.190-90", "98.765.321-0", "Fabiane", "F", "22/10/1969","Doutorado","UFCG"),
("462.412.530-42", "35.369.287-5", "Gabriela", "O", "08/08/1990","Doutorado","UFRJ"),
("761.582.160-62", "25.269.458-0", "Hugo", "M", "07/03/1992","Doutorado","USP"),
("195.863.480-83", "65.642.699-0", "Isabela", "F", "01/10/1984","Doutorado","UFSCAR"),
("898.511.090-02", "24.265.249-9", "Jhonathan", "O", "15/12/1988","Doutorado","USP");

INSERT INTO avaliador VALUES
(0, "766.401.940-07", "11.111.111-1", "Antonio", "M", "22/12/1973", "mestrado", "USP"),
(0, "369.280.360-52", "22.222.222-2", "Barbara", "F", "26/01/1970", "mestrado", "USP"),
(0, "729.721.930-40", "33.333.333-3", "Cristiano", "M", "14/11/1969", "Doutorado", "UNICAMP"),
(0, "589.850.040-50", "44.444.444-4", "Dulci", "F", "29/06/1958", "Doutorado", "UNICAMP"),
(0, "397.394.750-35", "55.555.555-5", "Edmundo", "M", "09/02/1972", "Doutorado", "UNICAMP");

INSERT INTO projeto VALUES
(0, "1001", "305.435.480-66", "USJT", "10", "17000.00", "Contandando"),
(0, "7003", "898.511.090-02", "USP", "5", "5000.00", "Filosofando"),
(0, "5003", "761.582.160-62", "USP", "3", "15000.00", "Força da agricultura"),
(0, "5003", "761.582.160-62", "USP", "5", "9590.00", "Pecuaria restrita"),
(0, "5003", "761.582.160-62", "USP", "7", "12500.00", "O campo e o agricultor"),
(0, "2002", "195.863.480-83", "UFSCAR", "9", "6500.00", "Mutação em humanos"),
(0, "2002", "195.863.480-83", "UFSCAR", "2", "1595.98", "Clonagem de plantas"),
(0, "3001", "462.412.530-42", "UFRJ", "3", "2050.00", "Testando plantas"),
(0, "3001", "305.435.480-66", "USJT", "15", "2000.00", "Resistencia de pontes"),
(0, "2001", "482.287.210-64", "Mackenzie", "3", "5000.00", "Vacinas veterinarias"),
(0, "2001", "482.287.210-64", "Mackenzie", "5", "7000.00", "Soros anticoagulantes"),
(0, "4003", "961.948.130-59", "UNICAMP", "5", "6000.00", "Tratamentos testes"),
(0, "4003", "961.948.130-59", "UNICAMP", "12", "2000.00", "Tratamentos testes 2"),
(0, "4001", "102.691.420-53", "USP", "24", "50000.00", "Tratamentos contra diabetes"),
(0, "7002", "898.511.090-02", "USP", "2", "800.00", "Filosofia na arte Romana"),
(0, "4001", "613.230.190-90", "UFCG" , "8", "10000.00", "Desenvolvimento de próteses"),
(0, "4001", "613.230.190-90", "UFCG" , "6", "5842.00", "Adaptação corporal a próteses"),
(0, "6003", "947.133.680-59", "USP" , "4", "3000.00", "Turismo local"),
(0, "6003", "947.133.680-59", "USP" , "1", "1200.00", "Turismo cultural no espaço urbano"),
(0, "7001", "898.511.090-02", "USP" , "2", "1200.00", "Filosofia e a arte");

INSERT INTO avaliacao VALUES
(0, "100001", "40001", "13/06/2019", "20/06/2019", "aprovado"),
(0, "100002", "40003", "14/06/2019", "21/06/2019", "aprovado"),
(0, "100003", "40004", "15/06/2019", "21/06/2019", "aprovado"),
(0, "100004", "40005", "16/06/2019", "22/06/2019", "aprovado"),
(0, "100005", "40003", "17/06/2019", "22/06/2019", "aprovado"),
(0, "100006", "40004", "18/06/2019", "23/06/2019", "aprovado"),
(0, "100007", "40005", "19/06/2019", "23/06/2019", "aprovado"),
(0, "100008", "40003", "20/06/2019", "25/06/2019", "aprovado"),
(0, "100009", "40001", "21/06/2019", "25/06/2019", "aprovado"),
(0, "100010", "40004", "22/06/2019", "26/06/2019", "aprovado"),
(0, "100011", "40005", "23/06/2019", "26/06/2019", "reprovado"),
(0, "100012", "40002", "24/06/2019", "28/06/2019", "reprovado"),
(0, "100013", "40002", "25/06/2019", "28/06/2019", "reprovado"),
(0, "100014", "40002", "26/06/2019", "30/06/2019", "reprovado"),
(0, "100015", "40003", "27/06/2019", "01/07/2019", "reprovado"),
(0, "100016", "40004", "28/06/2019", null, "não avaliado"),
(0, "100017", "40005", "29/06/2019", null, "não avaliado"),
(0, "100018", "40003", "30/06/2019", null, "não avaliado"),
(0, "100019", "40004", "30/06/2019", null, "não avaliado"),
(0, "100020", "40005", "30/06/2019", null, "não avaliado");

SELECT * FROM  avaliacao;
UPDATE avaliacao set resposta = "aprovado" where cod_avaliacao = 80001;