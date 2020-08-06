import React, { useContext } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
} from 'react-native';

import generalinfoStyles from '../styles/generalinfo';
import CustomTextInfo from '../components/CustomTextInfo';
import Title from '../components/Title';
import Footnote from '../components/Footnote';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const DisponibilityScreen = () => {
  const { state: { translate } } = useContext(I18nContext);

  return (
    <View style={styles.container}>
      <Title titleText="Sibipiruna" />
      <View style={styles.form}>

          <CustomTextInfo
            infoTitle={translate('DISPONIBILITY')}
            infoValue={translate('EMPTY_VALUE')}
          />

          <CustomTextInfo
            infoTitle={translate('DISPONIBILITY_ACCESS_CONDITIONS')}
            infoValue={translate('EMPTY_VALUE')}
          />

          <CustomTextInfo
            infoTitle={translate('DISPONIBILITY_CONDITIONS')}
            infoValue={translate('EMPTY_VALUE')}
          />

          <CustomTextInfo
            infoTitle={translate('DISPONIBILITY_PROTECTION')}
            infoValue={translate('EMPTY_VALUE')}
          />

          <CustomTextInfo
            infoTitle={translate('DISPONIBILITY_PROTECTION_INSTITUTE')}
            infoValue={translate('EMPTY_VALUE')}
          />

          <CustomTextInfo
            infoTitle={translate('DISPONIBILITY_NUMBER_PROCESS')}
            infoValue={translate('EMPTY_VALUE')}
          />

          <CustomTextInfo
            infoTitle={translate('DISPONIBILITY_RETURN_DATE')}
            infoValue={translate('EMPTY_VALUE')}
          />

          <View style={styles.inputs}>

            <View style={styles.lastFormTitle}>
              <Text style={styles.formTitleText}>{translate('DISPONIBILITY_USE')}</Text>
            </View>
            <View style={styles.formInput}>
              <TextInput
                value={translate('EMPTY_VALUE')}
              />
            </View>

          </View>

      </View>
      <Footnote />
    </View>
  );
};

DisponibilityScreen.navigationOptions = () => ({
  headerTitle: 'Disponibilidade',
});

export default DisponibilityScreen;
