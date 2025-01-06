# Java_JDBC_Project

## Camada Database
  Responsável por ministrar conexão com Banco de Dados e determinadas Exceções dentro do sistema no que diz respeito ao próprio banco, como `DatabaseException` para erros relacionados à consultas ou transações, ou `DatabaseIntegrityException` para aqueles que vão de encontro com a lógica e integridade dos dados, como violações de chave estrangeira, por exemplo.

## Camada Entidade
  Responsável pela representação de tabelas e entidades do Banco de Dados do Sistema. Temos aqui `Department` e `Seller` com suas respectivas propriedades;

  #### Department
  
  | Atributo | Tipo de Dado | Descrição                         |
  |----------|--------------|-----------------------------------|
  | id       | Integer      | Identificador único do departamento |
  | name     | String       | Nome do departamento              |


  #### Seller
  
  | Atributo    | Tipo de Dado | Descrição                               |
  |-------------|--------------|-----------------------------------------|
  | id          | Integer      | Identificador único do vendedor         |
  | name        | String       | Nome do vendedor                        |
  | email       | String       | E-mail do vendedor                      |
  | birthDate   | LocalDate    | Data de nascimento do vendedor          |
  | baseSalary  | Double       | Salário base do vendedor                |
  | department  | Department   | Referência ao departamento do vendedor  |

## Camada `Model.Dao`
  Camada onde definimos as interfaces de interação e operações CRUD para as entidades. Aqui definimos não só os métodos como seus tipos e parâmetros.
  
  Aqui também temos o `DaoFactory`, que tem como papel principal inicializar as implementações de cada objeto com seu respectivo `DaoJDBC` (falamos mais sobre no próximo tópico). 

## Camada `Model.Dao.Impl` (`Impl` de Implementação)
  Camada responsável por carregar os métodos lógicos de persistência, são inicializados a partir do `DaoFactory` com o parâmetro `Database.getConnection()` que irá inicializar também a conexão do objeto com sua respectiva entidade no Banco, transmitindo a partir daí, qualquer interação entre eles.
  











