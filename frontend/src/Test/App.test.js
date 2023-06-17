import {render, screen} from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from '../App';

/* Mocked Components */
jest.mock("../components/Page/Home", () => () => <div>Mocked_Home</div>);
jest.mock("../components/Page/Products", () => () => <div>Mocked_Products</div>);
jest.mock("../components/Page/AboutUs", () => () => <div>Mocked_AboutUs</div>);
jest.mock("../components/Page/NotFound", () => () => <div>Mocked_NotFound</div>);
jest.mock("../components/GlobalNavigation", () => () => <div>Mocked_GlobalNavigation</div>);
jest.mock("../components/GlobalFooter", () => () => <div>Mocked_GlobalFooter</div>);
jest.mock("../components/Page/AddProduct", () => () => <div>Mocked_AddProduct</div>);


describe("Routing tests for App.js", () => {

    test("Default route should render Home component", () => {
      // Arrange

      // Act
      render(
        <MemoryRouter>
          <App/>
        </MemoryRouter>
      );

      // Assert
      expect(screen.getByText("Mocked_Home"))
      expect(screen.getByText("Mocked_GlobalNavigation"))
      expect(screen.getByText("Mocked_GlobalFooter"))
    });

    test("Products route should render products component", () => {
      // Arrange

      // Act
      render(
        <MemoryRouter initialEntries={["/products"]}>
          <App/>
        </MemoryRouter>
      );

      // Assert
      expect(screen.getByText("Mocked_Products"))
      expect(screen.getByText("Mocked_GlobalNavigation"))
      expect(screen.getByText("Mocked_GlobalFooter"))
    });

    test("AboutUs route should render AboutUs component", () => {
      // Arrange

      // Act
      render(
        <MemoryRouter initialEntries={["/about"]}>
          <App/>
        </MemoryRouter>
      );

      // Assert
      expect(screen.getByText("Mocked_AboutUs"))
      expect(screen.getByText("Mocked_GlobalNavigation"))
      expect(screen.getByText("Mocked_GlobalFooter"))
    });

    test("Unkown path should trigger NotFound component", () => {
        // Arrange
  
        // Act
        render(
          <MemoryRouter initialEntries={["/testNoPage3432"]}>
            <App/>
          </MemoryRouter>
        );
  
        // Assert
        expect(screen.getByText("Mocked_NotFound"))
      });

      test("addproducts path should render productForm component", () => {
        // Arrange
  
        // Act
        render(
          <MemoryRouter initialEntries={["/add"]}>
            <App/>
          </MemoryRouter>
        );
  
        // Assert
        expect(screen.getByText("Mocked_AddProduct"))
      });

    
});