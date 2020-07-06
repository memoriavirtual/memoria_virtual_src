import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const buttonStyles = () => ({
  buttonContainer: {
    width: wp('40%'),
    height: hp('8%'),
    borderRadius: 10,
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: wp('2%'),
    justifyContent: 'space-around',
    borderWidth: 2,
    borderColor: 'white',
    elevation: 5,
  },
  enabledButtonBackground: {
    backgroundColor: '#4488BB',
  },
  disabledButtonBackground: {
    backgroundColor: 'gray',
  },
  buttonLabel: {
    // flex: 0.5,
    width: wp('18%'),
    color: 'white',
    fontWeight: 'bold',
    fontSize: 16,
    // alignSelf: 'center',
    textAlign: 'center',
  },
  iconContainer: {
    // flex: 0.5,
    width: wp('6.5%'),
    height: hp('4%'),
    alignSelf: 'center',
  },
});

export default buttonStyles;
