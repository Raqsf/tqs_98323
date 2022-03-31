# Questions

## a) Identify a couple of examples on the use of AssertJ expressive methods chaining.
We can see the use of AssertJ expressive methods in the A_EmployeeRepositoryTest in the lines 37, 43, 52, 53, 59 and 75.

## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).
In B_EmployeeService_UnitTest, all of the tests mock the behavior of the repository, since they all use the employeeService which uses the employeeRepository (@Mock). The behavior of the employeeRepository is defined in the setUp method.

## c) What is the difference between standard @Mock and @MockBean?
@Mock is functionally the same as Mockito.mock() and it is used for unit testing. A mock is created for the collaborator classes of the class that we are testing.
@MockBean is used for integration testing in a specific Spring Context. This context needs some beans, that if don't provided, the context won't start. To add new beans to that context we use the @MockBean annotation.
They both create mock objects.

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?
This file allows us to override properties from a file or use the default ones to run a test.


## e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?
In the C test (@WebMvcTest) we are just testing the EmployeeController creating a mock of the service. 
D and E use the full Spring Boot Application (@SpringBootTest), but D uses a servlet to access the server context through the MockMvc and E uses a REST client through TestRestTemplate.
