package com.felpeto.realestate.jpa.property;

import com.felpeto.realestate.domain.vo.LeisureItem;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class Generator {

  private static final String REGEX = "[0-9]{5}\\.[0-9]{1}\\.[0-9]{7}-[0-9]{2}";
  private static final BigDecimal ONE = BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP);
  private Faker faker = new Faker();

  @ParameterizedTest
  @EnumSource(LeisureItem.class)
  void createLeisureItemEntity(final LeisureItem item) {
    final var uuid = UUID.randomUUID();
    final var itemDescription = item.getDescription();

    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO leisure_item (");
    sb.append("uuid, ");
    sb.append("item");
    sb.append(") ");
    sb.append("VALUES (");
    sb.append("UNHEX(REPLACE('" + uuid + "', '-', '')), ");
    sb.append("'" + itemDescription + "'");
    sb.append(");");

    System.out.println(sb);

  }

  @RepeatedTest(100)
  void createPropertyEntity() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final var buildingArea = faker.number().numberBetween(1, 1000);
    final var city = faker.address().city().replace("'", "");
    final var complement = faker.ancient().god().replace("'", "");
//    final var condominiumLeisureItem = createLeisureItems();
    final var country = faker.address().country().replace("'", "");
    final var description = faker.animal().name().replace("'", "");
    final int garage = faker.number().numberBetween(1, 10);
    final var houseNumber = faker.number().numberBetween(1, 9999);
    final var isFurnished = faker.bool().bool();
    final var landSize = faker.number().numberBetween(1, 1000);
    final var neighborhood = faker.rickAndMorty().location().replace("'", "");
//    final var propertyLeisureItem = createLeisureItems();
    final var registration = faker.expression(faker.regexify(REGEX));
    final var rooms = faker.number().numberBetween(1, 10);
    final var state = faker.address().stateAbbr();
    final var streetName = faker.address().streetName().replace("'", "");
    final var uuid = UUID.randomUUID();
    final var zipcode = faker.address().zipCode();

    StringBuilder sb = new StringBuilder();
    sb.append("INSERT INTO property (");
    sb.append("uuid, ");
    sb.append("registration, ");
    sb.append("property_kind, ");
    sb.append("land_size, ");
    sb.append("building_area, ");
    sb.append("zipcode, ");
    sb.append("country, ");
    sb.append("state, ");
    sb.append("city, ");
    sb.append("neighborhood, ");
    sb.append("street_name, ");
    sb.append("house_number, ");
    sb.append("complement, ");
    sb.append("is_condominium, ");
    sb.append("condominium_price, ");
//    sb.append("condominium_leisure_items, ");
    sb.append("is_furnished, ");
    sb.append("is_rent, ");
    sb.append("rent_price, ");
    sb.append("is_sale, ");
    sb.append("sale_price, ");
    sb.append("garage, ");
    sb.append("rooms, ");
    sb.append("taxes, ");
    sb.append("description");
//    sb.append("property_leisure_items ");
    sb.append(") ");
    sb.append("VALUES (");

    sb.append("UNHEX(REPLACE('" + uuid + "', '-', '')), ");
    sb.append("'" + registration + "', ");
    sb.append("'" + propertyKind + "', ");
    sb.append(landSize + ", ");
    sb.append(buildingArea + ", ");
    sb.append("'" + zipcode + "', ");
    sb.append("'" + country + "', ");
    sb.append("'" + state + "', ");
    sb.append("'" + city + "', ");
    sb.append("'" + neighborhood + "', ");
    sb.append("'" + streetName + "', ");
    sb.append(houseNumber + ", ");
    sb.append("'" + complement + "', ");
    sb.append(true + ", ");
    sb.append(ONE + ", ");
//    sb.append("'" + condominiumLeisureItem + "', ");
    sb.append(isFurnished + ", ");
    sb.append(true + ", ");
    sb.append(ONE + ", ");
    sb.append(true + ", ");
    sb.append(ONE + ", ");
    sb.append(garage + ", ");
    sb.append(rooms + ", ");
    sb.append(ONE + ", ");
    sb.append("'" + description + "'");
//    sb.append("'" + propertyLeisureItem + "'");

    sb.append(");");

    System.out.println(sb);
  }

  @Test
  void createPropertyLeisureItemEntity() {
    final var queries = new HashSet<String>();

    while (queries.size() < 500) {
      final var propertyId = faker.number().numberBetween(1, 100);
      final var leisureItemId = faker.number().numberBetween(1, 37);
      final var query = "INSERT INTO property_leisure_item ("
          + "property_id, "
          + "leisure_item_id"
          + ") "
          + "VALUES ("
          + "'" + propertyId + "', "
          + "'" + leisureItemId + "'"
          + ");";
      queries.add(query);
    }

    queries.forEach(System.out::println);
  }

  @Test
  void createCondominiumLeisureItemEntity() {
    final var queries = new HashSet<String>();

    while (queries.size() < 500) {
      final var propertyId = faker.number().numberBetween(1, 100);
      final var leisureItemId = faker.number().numberBetween(1, 37);
      final var query = "INSERT INTO condominium_leisure_item ("
          + "property_id, "
          + "leisure_item_id"
          + ") "
          + "VALUES ("
          + "'" + propertyId + "', "
          + "'" + leisureItemId + "'"
          + ");";
      queries.add(query);
    }

    queries.forEach(System.out::println);
  }
}
