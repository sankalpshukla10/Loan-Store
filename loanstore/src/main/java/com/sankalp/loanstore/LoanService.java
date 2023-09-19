import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLoan_ValidLoan() {
        // Arrange
        Loan loan = new Loan();
        loan.setLoanId("L1");
        loan.setCustomerId("C1");
        loan.setLenderId("LEN1");
        // Set other fields
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        // Act
        loanService.addLoan(loan);

        // Assert
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void testAddLoan_PaymentDateAfterDueDate() {
        // Arrange
        Loan loan = new Loan();
        loan.setLoanId("L1");
        loan.setCustomerId("C1");
        loan.setLenderId("LEN1");
        loan.setPaymentDate(LocalDate.of(2023, 5, 8)); // Payment date after due date
        loan.setDueDate(LocalDate.of(2023, 5, 7));
        // Set other fields

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.addLoan(loan);
        });

        assertEquals("Payment date can't be greater than the due date", exception.getMessage());
        verify(loanRepository, never()).save(loan);
    }
}
