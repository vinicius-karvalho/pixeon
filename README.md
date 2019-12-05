# Pixeon Challenge

## Build and Running
Requisitos:
-   Java8+ instalado (e acessível via command line);
-   Maven instalado (e acessível via command line);
-   Clone do repo efetuado com sucesso.

`mvn package && java -jar target/challenge-0.0.1.jar`

O container estará ouvindo em: `http://[hostname or ip]:8080`

## Criar uma nova instituição de saúde:
1.  POST **/v1/helthcare/institution**
2.  Request Body:
```json
    {
        "name" : "Clínica A",
        "cnpj" : "1000" 
    }
```
3.  Response:
```json
    {
        "id" : 1,
        "name" : "Clínica A",
        "cnpj" : "1000",
        "message" : "success",
        "coins" : 20
    }
```

## Criar um novo exame:
1.  POST **/v1/exam**
2.  Request Body:
```json
    {
        "healthcareId" : 1,
        "patientName" : "Vinícius Carvalho",
        "patientAge" : 36,
        "patientGender" : "MALE",
        "physicianName" : "André Carvalho",
        "physicianCRM" : "1000",
        "procedureName" : "Hemograma"
    }
```
3. Response:
```json
    {
        "id" : 2,
        "healthcareName" : "Clínica A",
        "patientName" : "Vinícius Carvalho",
        "patientAge" : 36,
        "patientGender" : "MALE",
        "physicianName" : "André Carvalho",
        "physicianCRM" : "1000",
        "procedureName" : "Hemograma",
        "message" : "Exame criado com sucesso"
    }
```
## Acessar um exame:
1.  GET **/v1/exam/{id}** ou **/v1/exam/{id}/{healthcareId}**
2.  Response:
```json
    {
        "id" : 2,
        "healthcareName" : "Clínica A",
        "patientName" : "Vinícius Carvalho",
        "patientAge" : 36,
        "patientGender" : "MALE",
        "physicianName" : "André Carvalho",
        "physicianCRM" : "1000",
        "procedureName" : "Hemograma"
    }
```
## Atualizar um exame:
1.  PUT **/v1/exam/{id}**
2.  Request Body:
```json
    {   
        "id" : 2,
        "patientName" : "Fernanda de Melo",
        "patientAge" : 36,
        "patientGender" : "FEMALE",
        "physicianName" : "José de Alcântara",
        "physicianCRM" : "1001",
        "procedureName" : "Colesterol"
    }
```
3. Response:
```json
    {
        "id" : 2,
        "healthcareName" : "Clínica A",
        "patientName" : "Fernanda de Melho",
        "patientAge" : 36,
        "patientGender" : "FEMALE",
        "physicianName" : "José de Alcântara",
        "physicianCRM" : "1001",
        "procedureName" : "Colesterol",
        "message" : "Exame atualizado com sucesso"
    }
```
## Excluir um exame:
1.  DELETE **/v1/exam/{id}**
2.  Response: status code igual a 200 em caso de sucesso.

## Response Bodies para o caso de exceções e violações de regras de negócio:
```json
    {
        "timestamp": "yyyy-MM-ddTHH:mm:ss.SSSz",
        "status": 404,
        "error": "Not Found",
        "message": "Instituição de saúde não encontrada",
        "path": "/v1/helthcare/institution/2"
    }
    {
        "timestamp": "yyyy-MM-ddTHH:mm:ss.SSSz",
        "status": 401,
        "error": "Unauthorized",
        "message": "Esta instituição não está autorizada a acessar este exame",
        "path": "/v1/exam/2/3"
    }
    {
        "timestamp": "yyyy-MM-ddTHH:mm:ss.SSSz",
        "status": 400,
        "error": "Bad Request",
        "message": "É necessário informar o nome do procedimento",
        "path": "/v1/exam"
    }
    ...
```

## Considerações Gerais:
-   O *content-type* de todos os requestBodies e responseBodies é **application/json; charset=utf-8**
-   Não é feito o uso de `i18n` (simplesmente por questões práticas);
-   Não é feita validação de CNPJ;
-   Não é feita validação de CRM (validação de formato/pattern);
-   Todos os campos de texto no banco possuem o tamanho máximo de 255 caracteres;
-   Sobre o requisito não funcional de subir várias instâncias da aplicação:
    -   Por questões de praticidate, a aplicação, por padrão, usa um banco de dados *embedded* em memória. Zero configurações necessárias;
    -   Entretanto, para que este requisito não funcional seja contemplado (N instâncias da app servindo requisições simultâneas) é necessário fazer o apontamento para um banco de dados relacional centralizado. Neste caso, é requerido:
        -   Editar o arquivo `application.properties` marcando a propriedade `datasource.embedded` para `false` e ajustar o dialeto do banco (caso a opção seja por MySQL, já tem o template de configuração pronto);
        -   Editar a classe `ChallengeApplication` modificando os valores das propriedades `driverClassName`, `url`, `username` e `password` (mais uma vez, caso seja MySQL os valores estão pré-configurados);
        -   Incluir dependência ao driver adequado no `pom.xml`, caso não seja MySQL8 (o driver jdbc para o mesmo já está embarcado na aplicação)
