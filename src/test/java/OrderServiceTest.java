import com.toy_order_service.orderservice.entity.LineItem;
import com.toy_order_service.orderservice.entity.Order;
import com.toy_order_service.orderservice.enums.LineItemType;
import com.toy_order_service.orderservice.enums.Status;
import com.toy_order_service.orderservice.exceptions.InvalidOrderStateException;
import com.toy_order_service.orderservice.exceptions.OrderNotFoundException;
import com.toy_order_service.orderservice.repository.OrderRepository;
import com.toy_order_service.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Clock clock;

    @InjectMocks
    private OrderService orderService;

    private List<LineItem> listLineItem;
    private Instant fixedTime;


    @BeforeEach
    void setUp() {
        fixedTime = Instant.parse("2026-11-27T10:00:00Z");

        var lineItem1 = new LineItem("Laptop1", LineItemType.ELECTRONICS, new BigDecimal("35.50"));
        listLineItem = List.of(lineItem1);
    }

    private Order createTestOrder(UUID id, Status status) {
        return new Order(id, status, "Mikhail", listLineItem, "comment", new BigDecimal("35.50"), fixedTime);
    }


    @Test
    void shouldCreateOrderSuccessfully() {
        when(clock.instant()).thenReturn(fixedTime);

        var orderTest = new Order("Mikhail", listLineItem, "comment", null, null, null);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        Order result = orderService.createOrder(orderTest);

        assertThat(result.getStatus()).isEqualTo(Status.NEW);
        assertThat(result.getCustomer()).isEqualTo(orderTest.getCustomer());
        assertThat(result.getCreatedAt()).isEqualTo(fixedTime);

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldOrderNotFound() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(id))
                .isInstanceOf(OrderNotFoundException.class);

        verify(orderRepository).findById(id);
    }

    @Test
    void shouldChangeOrderStatusFromNewToPaid() {
        UUID id = UUID.randomUUID();
        Order orderTest = createTestOrder(id, Status.NEW);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderTest));

        orderService.pay(id);
        assertThat(orderTest.getStatus()).isEqualTo(Status.PAID);

        verify(orderRepository).findById(id);
    }

    @Test
    void shouldChangeOrderStatusFromNewToCancelled() {
        UUID id = UUID.randomUUID();
        Order orderTest = createTestOrder(id, Status.NEW);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderTest));

        orderService.cancel(id);
        assertThat(orderTest.getStatus()).isEqualTo(Status.CANCELLED);

        verify(orderRepository).findById(id);
    }

    @Test
    void shouldThrowWhenChangingOrderStatusFromNewToShipped() {
        UUID id = UUID.randomUUID();
        Order orderTest = createTestOrder(id, Status.NEW);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderTest));

        assertThatThrownBy(() -> orderService.ship(id)).isInstanceOf(InvalidOrderStateException.class);

        verify(orderRepository).findById(id);
    }

    @Test
    void shouldChangeOrderStatusFromPaidToShipped() {
        UUID id = UUID.randomUUID();
        Order orderTest = createTestOrder(id, Status.PAID);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderTest));

        orderService.ship(id);
        assertThat(orderTest.getStatus()).isEqualTo(Status.SHIPPED);

        verify(orderRepository).findById(id);
    }

    @Test
    void shouldChangeOrderStatusFromPaidToCancelled() {
        UUID id = UUID.randomUUID();
        Order orderTest = createTestOrder(id, Status.PAID);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderTest));

        orderService.cancel(id);
        assertThat(orderTest.getStatus()).isEqualTo(Status.CANCELLED);

        verify(orderRepository).findById(id);
    }

    @Test
    void shouldThrowOnDoubleCancellation() {
        UUID id = UUID.randomUUID();
        Order orderTest = createTestOrder(id, Status.CANCELLED);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderTest));

        assertThatThrownBy(() -> orderService.cancel(id)).isInstanceOf(InvalidOrderStateException.class);

        verify(orderRepository).findById(id);
    }

    @Test
    void shouldThrowWhenOrderIsAlreadyShipped() {
        UUID id = UUID.randomUUID();
        Order orderTest = createTestOrder(id, Status.SHIPPED);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderTest));

        assertThatThrownBy(() -> orderService.ship(id)).isInstanceOf(InvalidOrderStateException.class);

        verify(orderRepository).findById(id);
    }

    @Test
    void shouldThrowWhenOrderIsAlreadyPaid() {
        UUID id = UUID.randomUUID();
        Order orderTest = createTestOrder(id, Status.PAID);
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderTest));

        assertThatThrownBy(() -> orderService.pay(id)).isInstanceOf(InvalidOrderStateException.class);

        verify(orderRepository).findById(id);
    }
}
