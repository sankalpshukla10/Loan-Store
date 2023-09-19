@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    public void addLoan(Loan loan) {
        // Add validation logic for payment date and due date
        if (loan.getPaymentDate().isAfter(loan.getDueDate())) {
            logger.warn("Loan {} has a payment date greater than the due date.", loan.getLoanId());
            throw new IllegalArgumentException("Payment date can't be greater than the due date");
        }

        loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(String loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with ID: " + loanId));
    }

    @Override
    public List<Loan> getLoansByCustomerId(String customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Loan> getLoansByLenderId(String lenderId) {
        return loanRepository.findByLenderId(lenderId);
    }
}
