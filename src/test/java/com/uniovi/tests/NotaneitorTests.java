package com.uniovi.tests;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.util.SeleniumUtils;

import org.junit.runners.MethodSorters;
//Prueba
//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class NotaneitorTests {
	//En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\monon\\Downloads\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
	//En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens automáticas):
	//static String PathFirefox65 = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	//static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	//Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}
	//Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp(){
		driver.navigate().to(URL);
	}
	//Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown(){
		driver.manage().deleteAllCookies();
	}
	//Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}
	//Al finalizar la última prueba
	@AfterClass
	static public void end() {
		//Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}



	//PR01. Registro de Usuario con datos válidos.
	@Test
	public void PR01() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "Nadeko", "Sengoku", "nadeko@nishioishin.jp", "123456","123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

	}
	//PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos).
	@Test
	public void PR02() {
		//Vamos a /signup
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "", "asd", "asd", "123456","123456");

		//Comprobamos el error de nombre vacio.
		PO_View.getP();
		PO_RegisterView.checkKey(driver, "Error.signup.name.empty",PO_Properties.getSPANISH());

		//Repetimos con apellido vacio.
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "asd", "", "asd", "123456","123456");
		PO_View.getP();
		PO_RegisterView.checkKey(driver, "Error.signup.surname.empty",PO_Properties.getSPANISH());

		//Repetimos con email vacio.
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "asd", "asd", "", "123456","123456");
		PO_View.getP();
		PO_RegisterView.checkKey(driver, "Error.signup.email.empty",PO_Properties.getSPANISH());
	}
	@Test
	//PR03. Registro de Usuario con datos inválidos(repetición de contraseña inválida).
	public void PR03() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "Nadekaso", "Sengoku", "ann@nishioishin.jp", "123456","23456");
		PO_View.getP();
		//Comprobamos el error de que la contraseña y su confirmacion no coenciden.
		PO_RegisterView.checkKey(driver, "Error.signup.password.notequal",PO_Properties.getSPANISH());



	}
	@Test
	//PR04. Registro de Usuario con datos inválidos (email existente).
	public void PR04() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "Nadeko", "Sengoku", "nadeko@nishioishin.jp", "123456","123456");
		PO_View.getP();
		//Comprobamos el error de email repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate",PO_Properties.getSPANISH());

	}

	//Inicio de sesión con datos válidos (administrador).
	@Test
	public void PR05() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"admin@email.com", "admin");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	//Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void PR06() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"nadeko@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	//Inicio de sesión con datos inválidos (usuario estándar,campo email y contraseña vacíos).
	//@Test
	public void PR07() {}

	//Inicio  de  sesión  con  datos  válidos  (usuario  estándar, email  existente,  pero  contraseña incorrecta).
	//@Test
	public void PR08() {}

	//Hacer click en la opción de salir de sesión y comprobar que se redirige a la página de inicio de sesión(Login).
	@Test
	public void PR09() {
		//Nos conectamos
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"nadeko@nishioishin.jp", "123456");
		//Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "Conectarse");
	}

	//Comprobarque el botón cerrar sesión no está visible si el usuario no está autenticado.
	@Test
	public void PR10() {
		try {
			//Intentamos clickar en desconectar. Accion que debido a otras pruebas sabemos que funciona.
			PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
			//Si lo conseguimos, el test a fracasado
			assertTrue(false);
		}
		catch(Exception e) {
			//Si no lo conseguimos el text es un exito
			assertTrue(true);
		}
		
	}

	//Mostrar  el  listado  deusuarios  y  comprobar  que  se  muestran  todos  los  que  existen  en  el sistema.
	@Test
	public void PR11() {
		//SeleniumUtils.esperarSegundos(driver, 5);
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"nadeko@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}
	//Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que corresponde con el listado usuarios existentes en el sistema
	@Test
	public void PR12() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"nadeko@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		WebElement buscar=this.driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		WebElement boton=this.driver.findElement(By.className("btn"));
		boton.click();
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);	
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}
	//Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se muestra la página que corresponde, con la lista de usuarios vacía.
	@Test
	public void PR13() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"nadeko@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		WebElement buscar=this.driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		buscar.sendKeys("Prueba inexistente");
		WebElement boton=this.driver.findElement(By.className("btn"));
		boton.click();
		List<WebElement> elements=driver.findElements(By.name("nombres"));
		assertTrue(elements.size() == 0);
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}
	//Hacer una búsqueda con un texto específico y comprobar que se muestra la página que corresponde, con la lista de usuarios en los que el texto especificados sea parte de su nombre, apellidos o de su email.
	@Test
	public void PR14() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"nadeko@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		WebElement buscar=this.driver.findElement(By.name("searchText"));
		buscar.click();
		buscar.clear();
		buscar.sendKeys("Koyomi");
		WebElement boton=this.driver.findElement(By.className("btn"));
		boton.click();
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
	}
	//Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario. Comprobar que la solicitud de amistad aparece en el listado de invitaciones (punto siguiente).
	@Test
	public void PR15() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"nadeko@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		List<WebElement> elements=driver.findElements(By.name("invite"));
		elements.get(1).click();
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"koyomi@nishioishin.jp", "123456");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'invite-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'invite/recieve')]");
		elementos.get(0).click();
		PO_View.checkElement(driver, "text", "Invitaciones Recividas");
		elements=driver.findElements(By.name("reciever"));
		assertTrue(elements.size() == 2);


	}
	//Desde el listado de usuarios de la aplicación, enviar una invitación de amistad a un usuario al que ya le habíamos enviado la invitación previamente. No debería dejarnos enviar la invitación, se podría ocultar el botón de enviar invitación o notificar que ya había sido enviada previamente.
	//@Test
	public void PR16() {

	}

	//Mostrar el listado de invitaciones de amistad recibidas. Comprobar con un listado que contenga varias invitaciones recibidas
	@Test
	public void PR17() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"koyomi@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'invite-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'invite/recieve')]");
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
	}
	//Sobre el listado de invitaciones recibidas. Hacer click en el botón/enlace de una de ellas y comprobar que dicha solicitud desaparece del listado de invitaciones.
	@Test
	public void PR18() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"koyomi@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'invite-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'invite/recieve')]");
		elementos.get(0).click();
		List<WebElement> elements=driver.findElements(By.name("add"));
		elements.get(1).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
	}

	//Mostrar el listado de amigos de un usuario. Comprobar que el listado contiene los amigos que deben ser.
	@Test
	public void PR19() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"koyomi@nishioishin.jp", "123456");
		PO_View.checkElement(driver, "text", "Lista de Usuarios");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'friend-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friends/list')]");
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free","//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
	}
	//Visualizar al menos cuatro páginas en Español/Inglés/Español (comprobando que algunas de las etiquetas cambian al idioma correspondiente). Ejemplo, Página principal/Opciones Principales de Usuario/Listado de Usuarios.
	@Test
	public void PR20() {
		PO_View.checkKey(driver, "home.welcome", PO_Properties.getSPANISH());
		PO_NavView.changeIdiom(driver,"btnEnglish" );
		PO_View.checkKey(driver, "home.welcome", PO_Properties.getENGLISH());
		PO_NavView.changeIdiom(driver,"btnSpanish" );

		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_View.checkKey(driver, "signup.title", PO_Properties.getSPANISH());
		PO_NavView.changeIdiom(driver,"btnEnglish" );
		PO_View.checkKey(driver, "signup.title", PO_Properties.getENGLISH());
		PO_NavView.changeIdiom(driver,"btnSpanish" );

		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_View.checkKey(driver, "login.title", PO_Properties.getSPANISH());
		PO_NavView.changeIdiom(driver,"btnEnglish" );
		PO_View.checkKey(driver, "login.title", PO_Properties.getENGLISH());
		PO_NavView.changeIdiom(driver,"btnSpanish" );

		PO_LoginView.fillForm(driver,"koyomi@nishioishin.jp", "123456");
		PO_View.checkKey(driver, "user.list.title", PO_Properties.getSPANISH());
		PO_NavView.changeIdiom(driver,"btnEnglish" );
		PO_View.checkKey(driver, "user.list.title", PO_Properties.getENGLISH());
		PO_NavView.changeIdiom(driver,"btnSpanish" );
	}

	//Intentar acceder sin estar autenticado a la opción de listado de usuarios. Se deberá volver al formulario de login.
	@Test
	public void PR21() {
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'user-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		PO_View.checkKey(driver, "login.title", PO_Properties.getSPANISH());
	}
	//Intentar acceder sin estar autenticado a la opción de listado de publicaciones de un usuario estándar. Se deberá volver al formulario de login.
	@Test
	public void PR22() {
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'user-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
		PO_View.checkKey(driver, "login.title", PO_Properties.getSPANISH());
	}
	//Estando autenticado como usuario estándar intentar acceder a una opción disponible solo para usuarios administradores (Se puede añadir una opción cualquiera en el menú). Se deberá indicar un mensaje de acción prohibida.
	@Test
	public void PR23() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver,"koyomi@nishioishin.jp", "123456");
		List<WebElement> elementos = PO_View.checkElement(driver, "text", "Admin");
		elementos.get(0).click();
		SeleniumUtils.textoPresentePagina(driver, "HTTP Status");
	}

}