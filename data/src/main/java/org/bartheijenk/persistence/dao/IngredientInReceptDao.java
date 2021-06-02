package org.bartheijenk.persistence.dao;

import org.bartheijenk.persistence.entity.IngredientInRecept;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IngredientInReceptDao extends Dao<IngredientInRecept, Long> {

}
